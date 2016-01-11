package org.maxwe.android.utils.sample.tablist;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import org.maxwe.android.utils.sample.R;

import java.util.LinkedList;

/**
 * Created by Pengwei Ding on 2016-01-11 12:18.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class TabListActivity extends Activity {
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.org_maxwe_android_utils_tablist);

        this.listView = (ListView)this.findViewById(R.id.list);
        LinkedList<String> strings = new LinkedList<>();
        for (int index=0;index< 20;index++){
            strings.add("a " + index);
        }
        this.listView.setAdapter(new ArrayAdapter(this,android.R.layout.simple_list_item_1,strings));
    }
}
