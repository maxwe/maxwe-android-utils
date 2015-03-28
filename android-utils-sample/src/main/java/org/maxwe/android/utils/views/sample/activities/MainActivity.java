package org.maxwe.android.utils.views.sample.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import org.maxwe.android.utils.views.image.MulitImage;
import org.maxwe.android.utils.views.sample.R;

import java.util.LinkedList;

/**
 * Created by dingpengwei on 10/23/14.
 */
public class MainActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinkedList<Integer> integers = new LinkedList<Integer>();
        integers.add(R.drawable.ic_launcher);
        integers.add(R.drawable.ic_launcher);
        integers.add(R.drawable.ic_launcher);
        integers.add(R.drawable.ic_launcher);
        integers.add(R.drawable.ic_launcher);
        integers.add(R.drawable.ic_launcher);
        integers.add(R.drawable.ic_launcher);
        MulitImage mulitImage = new MulitImage(this, integers);
        this.setContentView(mulitImage);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        }
        return false;
    }
}
