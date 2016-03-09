package org.maxwe.android.utils.sample.hexagon;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;
import org.maxwe.android.utils.sample.R;
import org.maxwe.android.utils.sample.activities.MainActivity;
import org.maxwe.android.utils.views.hexagon.HexagonContainer;
import org.maxwe.android.utils.views.hexagon.HexagonView;

import java.util.LinkedList;

/**
 * Created by Pengwei Ding on 2016-01-10 16:20.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class HexagonActivity extends Activity implements View.OnClickListener{
    private ScrollView scrollView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scrollView = new ScrollView(this);
        scrollView.setBackgroundColor(Color.RED);
        scrollView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        this.setContentView(scrollView);
//        this.setContentView(R.layout.org_maxwe_android_utils_hexagon2);//152 5168 2555
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.scrollView.removeAllViews();
        LinkedList<HexagonView> hexagonItems = new LinkedList<>();
        int initNum = 30;
        for (int index =0 ;index<initNum;index++){
            HexagonView hexagonView = new HexagonView(this);
            RelativeLayout relativeLayout = new RelativeLayout(this);
            relativeLayout.setBackgroundResource(R.drawable.org_maxwe_hexagon_bg);
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
            imageView.setImageResource(R.drawable.index_01);
            relativeLayout.addView(imageView);
            hexagonView.setOnClickListener(this);
            hexagonView.addView(relativeLayout);
            hexagonItems.add(hexagonView);
        }
        RelativeLayout hexagonContainer = new HexagonContainer(this, hexagonItems, 4);
        scrollView.addView(hexagonContainer);
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(this,"onClick",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, MainActivity.class);
        this.startActivity(intent);
    }
}
