package org.maxwe.android.utils.views.camera;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by Pengwei Ding on 2016-04-15 11:27.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class CameraPreview extends SurfaceView implements Camera.PreviewCallback, SurfaceHolder.Callback, Camera.AutoFocusCallback, Camera.ShutterCallback, Camera.PictureCallback {
    private SurfaceHolder surfaceHolder;
    private Paint paint = new Paint();
    private Camera camera;

    public CameraPreview(Context context) {
        super(context);
        this.init();
    }

    public CameraPreview(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init();
    }

    public CameraPreview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init();
    }

    private void init() {
        this.surfaceHolder = getHolder();
        this.surfaceHolder.addCallback(this);
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (this.camera == null) {
            //开启相机,不能放在构造函数中，不然不会显示画面.
            this.camera = Camera.open();
            try {
                this.camera.setPreviewDisplay(holder);
                this.camera.setDisplayOrientation(90);
                this.camera.setPreviewCallback(this);
                this.camera.startPreview();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        this.camera.setPreviewCallback(null);
        this.camera.stopPreview();//停止预览
        this.camera.release();//释放相机资源
        this.camera = null;
    }

    @Override
    public void onAutoFocus(boolean success, Camera camera) {
        if (success) {
            //设置参数,并拍照
            Camera.Parameters params = this.camera.getParameters();
            params.setPictureFormat(PixelFormat.RGBA_8888);
            params.setPreviewSize(640, 480);
            this.camera.setParameters(params);
            this.camera.takePicture(this, null, this);
        }
    }

    private SurfaceView surfaceView;

    public void setSurfaceView(SurfaceView surfaceView) {
        this.surfaceView = surfaceView;
    }

    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {
        System.out.println("=====数据长度=" + data.length);
        if (surfaceView != null) {
            Canvas canvas = surfaceView.getHolder().lockCanvas();
            Camera.Parameters parameters = camera.getParameters();
            int imageFormat = parameters.getPreviewFormat();
            int w = parameters.getPreviewSize().width;
            int h = parameters.getPreviewSize().height;
            Rect rect = new Rect(0, 0, w, h);
            YuvImage yuvImg = new YuvImage(data, imageFormat, w, h, null);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            yuvImg.compressToJpeg(rect, 100, byteArrayOutputStream);
            Bitmap bitmap = BitmapFactory.decodeByteArray(byteArrayOutputStream.toByteArray(), 0, byteArrayOutputStream.size());
            if (bitmap != null) {
                System.out.println("=====编码的Bitmap成功=====");
                canvas.drawBitmap(bitmap, 0, 0, this.paint);
            } else {
                System.out.println("=====编码的Bitmap是空=====");
            }
            surfaceView.getHolder().unlockCanvasAndPost(canvas);
        }
//        Canvas canvas = this.surfaceHolder.lockCanvas();
//        System.out.println("=====数据长度=" + data.length + ", canvas = null ? =" + canvas == null + "======");
//        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
//        if (bitmap != null){
//            System.out.println("=====编码的Bitmap是空=====");
//            canvas.drawBitmap(bitmap,0,0,this.paint);
//        }
//        if (canvas != null){
//            this.surfaceHolder.unlockCanvasAndPost(canvas);
//        }
    }

    @Override
    public void onShutter() {

    }

    @Override
    public void onPictureTaken(byte[] data, Camera camera) {

//        try {
//            Bitmap bm = BitmapFactory.decodeByteArray(data, 0, data.length);
//            File file = new File(filePath);
//            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
//            bm.compress(Bitmap.CompressFormat.JPEG, 100, bos);//将图片压缩到流中
//            bos.flush();//输出
//            bos.close();//关闭
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
