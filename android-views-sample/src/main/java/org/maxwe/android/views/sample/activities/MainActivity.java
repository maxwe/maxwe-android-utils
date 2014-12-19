package org.maxwe.android.views.sample.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import org.maxwe.android.views.treelist.TreeListView;
import org.maxwe.json.Json;
import org.maxwe.json.JsonObject;

/**
 * Created by dingpengwei on 10/23/14.
 */
public class MainActivity extends Activity {

    private TreeListView treeListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.treeListView = new TreeListView(this);
        String json = "{\n" +
                "    \"nodeList\": [\n" +
                "        {\"pid\":\"00\",\"id\":\"01\",\"name\":\"北京\",\"otherInfo\":{},\"nodeList\":[{\"pid\":\"01\",\"id\":\"0101\",\"name\":\"海淀\",\"otherInfo\":{},\"nodeList\":[]}]},\n" +
                "        {\"pid\":\"00\",\"id\":\"02\",\"name\":\"天津\",\"otherInfo\":{},\"nodeList\":[{\"pid\":\"02\",\"id\":\"0201\",\"name\":\"南开\",\"otherInfo\":{},\"nodeList\":[]}]},\n" +
                "        {\"pid\":\"00\",\"id\":\"03\",\"name\":\"上海\",\"otherInfo\":{},\"nodeList\":[{\"pid\":\"03\",\"id\":\"0301\",\"name\":\"静安\",\"otherInfo\":{},\"nodeList\":[]}]},\n" +
                "        {\"pid\":\"00\",\"id\":\"04\",\"name\":\"重庆\",\"otherInfo\":{},\"nodeList\":[{\"pid\":\"04\",\"id\":\"0401\",\"name\":\"巴南\",\"otherInfo\":{},\"nodeList\":[]}]},\n" +
                "        {\"pid\":\"00\",\"id\":\"05\",\"name\":\"深圳\",\"otherInfo\":{},\"nodeList\":[{\"pid\":\"05\",\"id\":\"0501\",\"name\":\"龙华\",\"otherInfo\":{},\"nodeList\":[]}]},\n" +
                "        {\"pid\":\"00\",\"id\":\"06\",\"name\":\"河南\",\"otherInfo\":{},\"nodeList\":[{\"pid\":\"06\",\"id\":\"0601\",\"name\":\"郑州\",\"otherInfo\":{},\"nodeList\":[]}]}\n" +
                "    ]\n" +
                "}";
        this.treeListView.setJsonObject((JsonObject)Json.parse(json));

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
