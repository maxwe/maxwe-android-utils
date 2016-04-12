package org.maxwe.android.utils.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import org.maxwe.android.utils.sample.editer.EditerActivity;
import org.maxwe.android.utils.sample.hexagon.HexagonActivity;
import org.maxwe.android.utils.sample.progress.ProgressActivity;
import org.maxwe.android.utils.sample.tablist.TabListActivity;
import org.maxwe.android.utils.sample.webview.MyWebViewActivity;
import org.maxwe.android.utils.sample.webview.WebViewCameraActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
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
        } else if (id == R.id.WebView_MD) {
            Intent intent = new Intent(this, MyWebViewActivity.class);
            this.startActivity(intent);
        } else if (id == R.id.WebView_Camera) {
            Intent intent = new Intent(this, WebViewCameraActivity.class);
            this.startActivity(intent);
        } else if (id == R.id.progress) {
            Intent intent = new Intent(this, ProgressActivity.class);
            this.startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
