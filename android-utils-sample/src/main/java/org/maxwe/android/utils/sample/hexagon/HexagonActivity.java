package org.maxwe.android.utils.sample.hexagon;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import org.maxwe.android.utils.sample.R;
import org.maxwe.android.utils.views.hexagon.HexagonContainer;
import org.maxwe.android.utils.views.hexagon.HexagonView;

import java.util.LinkedList;

/**
 * Created by Pengwei Ding on 2016-01-10 16:20.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class HexagonActivity extends Activity implements View.OnClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinkedList<HexagonView> hexagonItems = new LinkedList<>();
        int initNum = 15;
        for (int index =0 ;index<initNum;index++){
            HexagonView hexagonView = new HexagonView(this);
            hexagonView.setOnClickListener(this);
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(R.drawable.index_01);
            hexagonView.addView(imageView);
            hexagonItems.add(hexagonView);
        }
        this.setContentView(new HexagonContainer(this, hexagonItems,4));
//        this.setContentView(R.layout.org_maxwe_android_utils_hexagon);
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(this,"onClick",Toast.LENGTH_LONG).show();
    }
}
