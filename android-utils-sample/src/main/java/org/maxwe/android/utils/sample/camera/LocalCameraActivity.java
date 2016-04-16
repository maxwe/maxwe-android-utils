package org.maxwe.android.utils.sample.camera;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.RelativeLayout;

import org.maxwe.android.utils.sample.R;
import org.maxwe.android.utils.views.camera.GameDisplay;

/**
 * Created by Pengwei Ding on 2016-04-15 11:37.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class LocalCameraActivity extends Activity {
    private GameDisplay gameDisplay;
    int screenWidth,screenHeight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.org_maxwe_android_utils_camera);
//
//        DisplayMetrics dm = getResources().getDisplayMetrics();
//        screenWidth = dm.widthPixels;
//        screenHeight = dm.heightPixels;
//
//        gameDisplay= new GameDisplay(this,dm.widthPixels,dm.heightPixels);
//        //加入到当前activity的layout中
//        RelativeLayout root = (RelativeLayout) findViewById(R.id.root);
//        root.addView(gameDisplay,0);
    }
}
