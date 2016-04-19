package org.maxwe.android.utils.sample.camera;

import android.app.Activity;
import android.os.Bundle;

import org.maxwe.android.utils.sample.R;
import org.maxwe.android.utils.views.camera.CameraFilterView;

/**
 * Created by Pengwei Ding on 2016-04-15 11:37.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class LocalCameraActivity extends Activity {
    private CameraFilterView camera_filter_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.org_maxwe_android_utils_camera);
        this.camera_filter_view = (CameraFilterView) this.findViewById(R.id.camera_filter_view);



//        这个实现方式是SurfaceView + SurfaceTexture实现，DecodeToImage算法比较慢，耗时200ms左右
//        DisplayMetrics dm = getResources().getDisplayMetrics();
//        screenWidth = dm.widthPixels;
//        screenHeight = dm.heightPixels;
//
//        gameDisplay= new GameDisplay(this,dm.widthPixels,dm.heightPixels);
//        //加入到当前activity的layout中
//        RelativeLayout root = (RelativeLayout) findViewById(R.id.root);
//        root.addView(gameDisplay,0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.camera_filter_view.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.camera_filter_view.onPause();
    }
}
