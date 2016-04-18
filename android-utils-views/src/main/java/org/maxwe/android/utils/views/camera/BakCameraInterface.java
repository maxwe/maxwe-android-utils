package org.maxwe.android.utils.views.camera;

import java.io.IOException;

import android.graphics.SurfaceTexture;
import android.hardware.Camera;

public class BakCameraInterface {
    private Camera mCamera;
//    private Camera.Parameters mParams;
    private boolean isPreviewing = false;
    private float mPreviwRate = -1f;
    private static BakCameraInterface mCameraInterface;
    public interface CamOpenOverCallback {
        void cameraHasOpened();
    }

    private BakCameraInterface() {

    }

    public static synchronized BakCameraInterface getInstance() {
        if (mCameraInterface == null) {
            mCameraInterface = new BakCameraInterface();
        }
        return mCameraInterface;
    }

    public void doOpenCamera(CamOpenOverCallback callback) {
        if (mCamera == null) {
            mCamera = Camera.open();
            if (callback != null) {
                callback.cameraHasOpened();
            }
        } else {
            doStopCamera();
        }
    }

//    public void doStartPreview(SurfaceHolder holder, float previewRate) {
//        if (isPreviewing) {
//            mCamera.stopPreview();
//            return;
//        }
//        if (mCamera != null) {
//            try {
//                mCamera.setPreviewDisplay(holder);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            initCamera(previewRate);
//        }
//    }

    public void doStartPreview(SurfaceTexture surface, float previewRate) {
        if (isPreviewing) {
            mCamera.stopPreview();
            return;
        }
        if (mCamera != null) {
            try {
                mCamera.setPreviewTexture(surface);
            } catch (IOException e) {
                e.printStackTrace();
            }
            initCamera(previewRate);
        }
    }

    public void doStopCamera() {
        if (null != mCamera) {
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            isPreviewing = false;
            mPreviwRate = -1f;
            mCamera.release();
            mCamera = null;
        }
    }

    public void doTakePicture() {
        //if (isPreviewing && (mCamera != null)) {
            //mCamera.takePicture(mShutterCallback, null, mJpegPictureCallback);
//        }
    }

    public boolean isPreviewing() {
        return isPreviewing;
    }


    private void initCamera(float previewRate) {
        if (mCamera != null) {
//            mParams = mCamera.getParameters();
//            mParams.setPictureFormat(ImageFormat.JPEG);
////			BakCamParaUtil.getInstance().printSupportPictureSize(mParams);
////			BakCamParaUtil.getInstance().printSupportPreviewSize(mParams);
//            Size pictureSize = BakCamParaUtil.getInstance().getPropPictureSize(mParams.getSupportedPictureSizes(), previewRate, 800);
//            mParams.setPictureSize(pictureSize.width, pictureSize.height);
//            Size previewSize = BakCamParaUtil.getInstance().getPropPreviewSize(mParams.getSupportedPreviewSizes(), previewRate, 800);
//            mParams.setPreviewSize(previewSize.width, previewSize.height);
//            mCamera.setDisplayOrientation(90);
////			BakCamParaUtil.getInstance().printSupportFocusMode(mParams);
//            List<String> focusModes = mParams.getSupportedFocusModes();
//            if (focusModes.contains("continuous-video")) {
//                mParams.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
//            }
//            mCamera.setParameters(mParams);
            mCamera.startPreview();
            isPreviewing = true;
            mPreviwRate = previewRate;
//            mParams = mCamera.getParameters();
        }
    }
//
//
//    ShutterCallback mShutterCallback = new ShutterCallback() {
//        public void onShutter() {
//        }
//    };
//    PictureCallback mRawCallback = new PictureCallback() {
//        public void onPictureTaken(byte[] data, Camera camera) {
//        }
//    };
//
//    PictureCallback mJpegPictureCallback = new PictureCallback() {
//        public void onPictureTaken(byte[] data, Camera camera) {
//            Bitmap b = null;
//            if (null != data) {
//                b = BitmapFactory.decodeByteArray(data, 0, data.length);
//                mCamera.stopPreview();
//                isPreviewing = false;
//            }
//            if (null != b) {
//                Bitmap rotaBitmap = BakImageUtil.getRotateBitmap(b, 90.0f);
//                BakFileUtil.saveBitmap(rotaBitmap);
//            }
//            mCamera.startPreview();
//            isPreviewing = true;
//        }
//    };


}
