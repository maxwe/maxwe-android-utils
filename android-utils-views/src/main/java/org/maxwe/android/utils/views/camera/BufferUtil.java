package org.maxwe.android.utils.views.camera;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * Created by Pengwei Ding on 2016-04-18 16:37.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class BufferUtil {

    public static FloatBuffer floatArrayToBuffer(float[] floatArray){
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(floatArray.length * 4);
        byteBuffer.order(ByteOrder.nativeOrder());
        FloatBuffer floatBuffer = byteBuffer.asFloatBuffer();
        floatBuffer.put(floatArray);
        floatBuffer.position(0);
        return floatBuffer;
    }

    public static IntBuffer intArrayToBuffer(int[] intArray){
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(intArray.length*4);
        byteBuffer.order(ByteOrder.nativeOrder());
        IntBuffer intBuffer = byteBuffer.asIntBuffer();
        intBuffer.put(intArray);
        intBuffer.position(0);
        return intBuffer;
    }
}
