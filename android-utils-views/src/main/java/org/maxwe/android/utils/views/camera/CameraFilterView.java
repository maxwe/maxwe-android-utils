package org.maxwe.android.utils.views.camera;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.opengl.GLES11Ext;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Pengwei Ding on 2016-04-16 14:46.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class CameraFilterView extends GLSurfaceView implements GLSurfaceView.Renderer, SurfaceTexture.OnFrameAvailableListener {
    private CameraEngine cameraEngine = new CameraEngine();
    private SurfaceTexture surfaceTexture;
    private DirectDrawer directDrawer;

    public CameraFilterView(Context context) {
        super(context);
        this.init();
    }

    public CameraFilterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init();
    }

    private void init() {
        this.setEGLContextClientVersion(2);
        this.setRenderer(this);
        this.setRenderMode(RENDERMODE_WHEN_DIRTY);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        int textureID = createTextureID();
        this.surfaceTexture = new SurfaceTexture(textureID);
        this.surfaceTexture.setOnFrameAvailableListener(this);
        this.directDrawer = new DirectDrawer(textureID);
        CameraInterface.getInstance().doOpenCamera(null);
        System.out.println("=====onSurfaceCreated=====");
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        System.out.println("=====onSurfaceChanged=====");
        GLES20.glViewport(0, 0, width, height);
        if(!CameraInterface.getInstance().isPreviewing()){
            CameraInterface.getInstance().doStartPreview(this.surfaceTexture, 1.33f);
        }
    }


    @Override
    public void onDrawFrame(GL10 gl) {
        System.out.println("=====onDrawFrame=====");
        GLES20.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        this.surfaceTexture.updateTexImage();
        float[] mtx = new float[16];
        this.surfaceTexture.getTransformMatrix(mtx);
        this.directDrawer.draw(mtx);
    }

    @Override
    public void onFrameAvailable(SurfaceTexture surfaceTexture) {
        System.out.println("=====onFrameAvailable=====");
        this.requestRender();
    }


    @Override
    public void onPause() {
        super.onPause();
        CameraInterface.getInstance().doStopCamera();
    }

    public SurfaceTexture _getSurfaceTexture(){
        return this.surfaceTexture;
    }

    private int createTextureID() {
        int[] texture = new int[1];
        GLES20.glGenTextures(1, texture, 0);
        GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, texture[0]);
        GLES20.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
        GLES20.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
        GLES20.glTexParameteri(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GL10.GL_TEXTURE_WRAP_S, GL10.GL_CLAMP_TO_EDGE);
        GLES20.glTexParameteri(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GL10.GL_TEXTURE_WRAP_T, GL10.GL_CLAMP_TO_EDGE);
        return texture[0];
    }
}
