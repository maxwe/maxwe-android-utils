package org.maxwe.android.views.sample.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import org.maxwe.android.views.treelist.TreeListView;

/**
 * Created by dingpengwei on 10/23/14.
 */
public class MainActivity extends Activity {

    private TreeListView treeListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.treeListView = new TreeListView(this);
        this.setContentView(this.treeListView);
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
