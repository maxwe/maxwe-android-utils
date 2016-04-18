package org.maxwe.android.utils.views.camera;

import android.graphics.SurfaceTexture;
import android.hardware.Camera;

import java.io.IOException;

/**
 * Created by Pengwei Ding on 2016-04-16 15:09.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class CameraEngine {
    private static CameraEngine cameraEngine;

    private CameraEngine(){}

    public static synchronized CameraEngine getInstance() {
        if (cameraEngine == null) {
            cameraEngine = new CameraEngine();
        }
        return cameraEngine;
    }

    private Camera camera;
    private boolean isPreviewing;

    public boolean isPreviewing() {
        return isPreviewing;
    }

    public void onSurfaceCreated() {
        if (this.camera == null) {
            //开启相机,不能放在构造函数中，不然不会显示画面.
            Camera.CameraInfo info=new Camera.CameraInfo();
            for (int i=0; i < Camera.getNumberOfCameras(); i++) {
                Camera.getCameraInfo(i, info);
                if (info.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                    camera=Camera.open(i);
                }
            }
            if (camera == null){
                this.camera = Camera.open();
            }
            this.camera.setDisplayOrientation(90);
        }
    }

    public void startPreview(SurfaceTexture surfaceTexture) {
        try {
            if (!this.isPreviewing){
                this.camera.setPreviewTexture(surfaceTexture);
                this.camera.startPreview();
                this.isPreviewing = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void onSurfaceDestroyed() {
        if (this.camera != null) {
            this.camera.setPreviewCallback(null);
            this.camera.stopPreview();//停止预览
            this.camera.release();//释放相机资源
            this.camera = null;
        }
        this.isPreviewing = false;
    }

}
