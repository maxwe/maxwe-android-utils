package org.maxwe.android.utils.sample.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import org.maxwe.android.utils.sample.R;
import org.maxwe.android.utils.sample.editer.EditerActivity;
import org.maxwe.android.utils.sample.hexagon.HexagonActivity;
import org.maxwe.android.utils.sample.tablist.TabListActivity;
import org.maxwe.android.utils.sample.webview.MyWebViewActivity;

/**
 * Created by dingpengwei on 10/23/14.
 */
public class MainActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.hexagon) {
            Intent intent = new Intent(this, HexagonActivity.class);
            this.startActivity(intent);
        } else if (id == R.id.material_design_tablist) {
            Intent intent = new Intent(this, TabListActivity.class);
            this.startActivity(intent);
        } else if (id == R.id.editer) {
            Intent intent = new Intent(this, EditerActivity.class);
            this.startActivity(intent);
        } else if (id == R.id.webview) {
            Intent intent = new Intent(this, MyWebViewActivity.class);
            this.startActivity(intent);
        }
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
