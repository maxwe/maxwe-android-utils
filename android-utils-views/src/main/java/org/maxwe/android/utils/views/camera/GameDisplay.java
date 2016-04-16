package org.maxwe.android.utils.views.camera;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ImageFormat;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;
import java.util.List;

/**
 * Created by Pengwei Ding on 2016-04-15 22:39.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class GameDisplay extends SurfaceView implements SurfaceHolder.Callback, Camera.PreviewCallback {

    public final static String TAG="GameDisplay";

    private static final int MAGIC_TEXTURE_ID = 10;
    public static final int DEFAULT_WIDTH=800;
    public static final int DEFAULT_HEIGHT=480;
    public static final int BLUR = 0;
    public static final int CLEAR = BLUR + 1;
    public SurfaceHolder gHolder;
    public SurfaceTexture gSurfaceTexture;
    public Camera gCamera;
    public byte gBuffer[];
    public int textureBuffer[];
    private int bufferSize;
    private Camera.Parameters parameters;
    public int previewWidth, previewHeight;
    public int screenWidth, screenHeight;
    public Bitmap gBitmap;


    public GameDisplay(Context context,int screenWidth,int screenHeight) {
        super(context);
        gHolder=this.getHolder();
        gHolder.addCallback(this);
        gHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        gSurfaceTexture=new SurfaceTexture(MAGIC_TEXTURE_ID);
        this.screenWidth=screenWidth;
        this.screenHeight=screenHeight;
    }


    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,int height) {
        parameters = gCamera.getParameters();
        List<Camera.Size> preSize = parameters.getSupportedPreviewSizes();
        previewWidth = preSize.get(0).width;
        previewHeight = preSize.get(0).height;
        for (int i = 1; i < preSize.size(); i++) {
            double similarity = Math
                    .abs(((double) preSize.get(i).height / screenHeight)
                            - ((double) preSize.get(i).width / screenWidth));
            if (similarity >Math.abs(((double) previewHeight / screenHeight)
                    - ((double) previewWidth / screenWidth))) {
                previewWidth = preSize.get(i).width;
                previewHeight = preSize.get(i).height;
            }
        }

        gBitmap= Bitmap.createBitmap(previewWidth, previewHeight, Bitmap.Config.ARGB_8888);
        parameters.setPreviewSize(previewWidth, previewHeight);
        gCamera.setParameters(parameters);
        bufferSize = previewWidth * previewHeight;
        textureBuffer=new int[bufferSize];
        bufferSize  = bufferSize * ImageFormat.getBitsPerPixel(parameters.getPreviewFormat()) / 8;
        gBuffer = new byte[bufferSize];
        gCamera.addCallbackBuffer(gBuffer);
        gCamera.setPreviewCallbackWithBuffer(this);
        gCamera.startPreview();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.v(TAG, "GameDisplay surfaceCreated");
        if (gCamera == null) {
            gCamera = Camera.open(0);
            gCamera.setDisplayOrientation(90);
        }
        try {
            gCamera.setPreviewTexture(gSurfaceTexture);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.v(TAG, "GameDisplay surfaceDestroyed");
        gCamera.stopPreview();
        gCamera.release();
        gCamera=null;
    }
    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {
        Log.v(TAG, "GameDisplay onPreviewFrame");
        camera.addCallbackBuffer(gBuffer);  //<----这句一点要加上.

        if(gCamera==null){   //if after release ,here also called; so do if.
            return ;
        }
        synchronized (gHolder)
        {
            int w = camera.getParameters().getPreviewSize().width;
            int h = camera.getParameters().getPreviewSize().height;
            int[] rgb = decodeYUV420SP(data, w, h);
            Canvas canvas = this.getHolder().lockCanvas();
            canvas.drawBitmap(rgb, 0, w, 0, 0, w, h, false, null);
            this.getHolder().unlockCanvasAndPost(canvas);
        }
    }

    public int[] decodeYUV420SP(byte[] yuv420sp, int width, int height) {
        final int frameSize = width * height;
        for (int j = 0, yp = 0; j < height; j++) {
            int uvp = frameSize + (j >> 1) * width, u = 0, v = 0;
            for (int i = 0; i < width; i++, yp++) {
                int y = (0xff & ((int) yuv420sp[yp])) - 16;
                if (y < 0) y = 0;
                if ((i & 1) == 0) {
                    v = (0xff & yuv420sp[uvp++]) - 128;
                    u = (0xff & yuv420sp[uvp++]) - 128;
                }

                int y1192 = 1192 * y;
                int r = (y1192 + 1634 * v);
                int g = (y1192 - 833 * v - 400 * u);
                int b = (y1192 + 2066 * u);

                if (r < 0) r = 0;
                else if (r > 262143) r = 262143;
                if (g < 0) g = 0;
                else if (g > 262143) g = 262143;
                if (b < 0) b = 0;
                else if (b > 262143) b = 262143;

                textureBuffer[yp] = 0xff000000 | ((r << 6) & 0xff0000) | ((g >> 2) &  0xff00) | ((b >> 10) & 0xff);

            }
        }
        return textureBuffer;
    }

}
