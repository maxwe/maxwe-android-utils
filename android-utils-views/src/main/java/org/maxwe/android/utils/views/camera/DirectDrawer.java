package org.maxwe.android.utils.views.camera;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.LinkedList;

import android.opengl.GLES11Ext;
import android.opengl.GLES20;
import android.opengl.Matrix;
import android.util.Log;

public class DirectDrawer {
    private final String vertexShaderCode =
            "attribute vec4 vPosition;" +
                    "attribute vec2 inputTextureCoordinate;" +
                    "varying vec2 textureCoordinate;" +
                    "void main()" +
                    "{" +
                    "gl_Position = vPosition;" +
                    "textureCoordinate = inputTextureCoordinate;" +
                    "}";


    private final String fragmentShaderCode =
            "#extension GL_OES_EGL_image_external : require\n"+
            "precision mediump float;" +
            "varying vec2 textureCoordinate;\n" +
            "uniform samplerExternalOES s_texture;\n" +
            "void main() {" +
            "  gl_FragColor = texture2D( s_texture, textureCoordinate );\n" +
            "}";
//
//
//
//    public static final String VERTEX_SHADER = "" +
//            "attribute vec4 position;\n" +
//            "attribute vec4 inputTextureCoordinate;\n" +
//            " \n" +
//            "varying vec2 textureCoordinate;\n" +
//            " \n" +
//            "void main()\n" +
//            "{\n" +
//            "    gl_Position = position;\n" +
//            "    textureCoordinate = inputTextureCoordinate.xy;\n" +
//            "}";
//
//    private final String fragmentShaderCode = "varying highp vec2 textureCoordinate;\n" +
//            "precision highp float;\n" +
//            "\n" +
//            "uniform sampler2D inputImageTexture;\n" +
//            "uniform vec2 singleStepOffset; \n" +
//            "uniform float strength;\n" +
//            "\n" +
//            "const highp vec3 W = vec3(0.299,0.587,0.114);\n" +
//            "\n" +
//            "\n" +
//            "void main()\n" +
//            "{ \n" +
//            "\tfloat threshold = 0.0;\n" +
//            "\t//pic1\n" +
//            "\tvec4 oralColor = texture2D(inputImageTexture, textureCoordinate);\n" +
//            "\t\n" +
//            "\t//pic2\n" +
//            "\tvec3 maxValue = vec3(0.,0.,0.);\n" +
//            "\t\n" +
//            "\tfor(int i = -2; i<=2; i++)\n" +
//            "\t{\n" +
//            "\t\tfor(int j = -2; j<=2; j++)\n" +
//            "\t\t{\n" +
//            "\t\t\tvec4 tempColor = texture2D(inputImageTexture, textureCoordinate+singleStepOffset*vec2(i,j));\n" +
//            "\t\t\tmaxValue.r = max(maxValue.r,tempColor.r);\n" +
//            "\t\t\tmaxValue.g = max(maxValue.g,tempColor.g);\n" +
//            "\t\t\tmaxValue.b = max(maxValue.b,tempColor.b);\n" +
//            "\t\t\tthreshold += dot(tempColor.rgb, W); \n" +
//            "\t\t}\n" +
//            "\t}\n" +
//            "\t//pic3\n" +
//            "\tfloat gray1 = dot(oralColor.rgb, W);\n" +
//            "\t\n" +
//            "\t//pic4\n" +
//            "\tfloat gray2 = dot(maxValue, W);\n" +
//            "\t\n" +
//            "\t//pic5\n" +
//            "\tfloat contour = gray1 / gray2;\n" +
//            "\t\n" +
//            "\tthreshold = threshold / 25.;\n" +
//            "\tfloat alpha = max(strength,gray1>threshold?1.0:(gray1/threshold));\n" +
//            "\t\n" +
//            "\tfloat result = contour * alpha + (1.0-alpha)*gray1;\n" +
//            "\t\n" +
//            "\tgl_FragColor = vec4(vec3(result,result,result), oralColor.w);\n" +
//            "} \n";

    private FloatBuffer vertexBuffer, textureVerticesBuffer;
    private ShortBuffer drawListBuffer;
    private final int mProgram;
    private int mPositionHandle;
    private int mTextureCoordHandle;


    private short drawOrder[] = {0, 1, 2, 0, 2, 3}; // order to draw vertices

    // number of coordinates per vertex in this array
    private static final int COORDS_PER_VERTEX = 2;

    private final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex

    static float squareCoords[] = {
            -1.0f, 1.0f,
            -1.0f, -1.0f,
            1.0f, -1.0f,
            1.0f, 1.0f,
    };

    static float textureVertices[] = {
            0.0f, 1.0f,
            1.0f, 1.0f,
            1.0f, 0.0f,
            0.0f, 0.0f,
    };

//    static float textureVertices[] = {
//            0.0f, 1.0f,
//            1.0f, 1.0f,
//            0.0f, 0.0f,
//            1.0f, 0.0f,
//    };

    private int texture;


    public DirectDrawer(int texture) {
        this.texture = texture;
        // initialize vertex byte buffer for shape coordinates
        ByteBuffer bb = ByteBuffer.allocateDirect(squareCoords.length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(squareCoords);
        vertexBuffer.position(0);

        // initialize byte buffer for the draw list
        ByteBuffer dlb = ByteBuffer.allocateDirect(drawOrder.length * 2);
        dlb.order(ByteOrder.nativeOrder());
        drawListBuffer = dlb.asShortBuffer();
        drawListBuffer.put(drawOrder);
        drawListBuffer.position(0);

        ByteBuffer bb2 = ByteBuffer.allocateDirect(textureVertices.length * 4);
        bb2.order(ByteOrder.nativeOrder());
        textureVerticesBuffer = bb2.asFloatBuffer();
        textureVerticesBuffer.put(textureVertices);
        textureVerticesBuffer.position(0);

        int vertexShader    = loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
        int fragmentShader  = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);

        mProgram = GLES20.glCreateProgram();             // create empty OpenGL ES Program
        GLES20.glAttachShader(mProgram, vertexShader);   // add the vertex shader to program
        GLES20.glAttachShader(mProgram, fragmentShader); // add the fragment shader to program
        GLES20.glLinkProgram(mProgram);
    }

    public void draw(float[] mtx)
    {
        GLES20.glUseProgram(mProgram);

        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, texture);

        // get handle to vertex shader's vPosition member
        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");

        // Enable a handle to the triangle vertices
        GLES20.glEnableVertexAttribArray(mPositionHandle);

        // Prepare the <insert shape here> coordinate data
        GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, vertexStride, vertexBuffer);

        mTextureCoordHandle = GLES20.glGetAttribLocation(mProgram, "inputTextureCoordinate");
        GLES20.glEnableVertexAttribArray(mTextureCoordHandle);

//        textureVerticesBuffer.clear();
//        textureVerticesBuffer.put( transformTextureCoordinates( textureVertices, mtx ));
//        textureVerticesBuffer.position(0);

        GLES20.glVertexAttribPointer(mTextureCoordHandle, COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, vertexStride, textureVerticesBuffer);
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, drawOrder.length, GLES20.GL_UNSIGNED_SHORT, drawListBuffer);

        // Disable vertex array
        GLES20.glDisableVertexAttribArray(mPositionHandle);
        GLES20.glDisableVertexAttribArray(mTextureCoordHandle);
    }
    private  int loadShader(int type, String shaderCode){

        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }
    private float[] transformTextureCoordinates( float[] coords, float[] matrix)
    {
        float[] result = new float[ coords.length ];
        float[] vt = new float[4];

        for ( int i = 0 ; i < coords.length ; i += 2 ) {
            float[] v = { coords[i], coords[i+1], 0 , 1  };
            Matrix.multiplyMV(vt, 0, matrix, 0, v, 0);
            result[i] = vt[0];
            result[i+1] = vt[1];
        }
        return result;
    }

}
