package org.maxwe.android.utils.sample.tablist;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.*;
import org.maxwe.android.utils.views.tablist.IContent;

/**
 * Created by Pengwei Ding on 2016-02-05 14:15.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class PageItem extends ListView implements AbsListView.OnScrollListener, AdapterView.OnItemClickListener, IContent {
    public PageItem(Context context) {
        super(context);
        this.init();
    }

    public PageItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init();
    }

    public PageItem(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.init();
    }

    private void init() {
        this.setOnItemClickListener(this);
        String[] strings = {
                "00", "01", "02",
                "03", "04", "05",
                "06", "07", "08",
                "09", "10", "11",
                "12", "13", "14",
                "15", "16", "17",
                "18", "19", "20",
                "21", "22", "23",
                "24", "25", "26",
                "27", "28", "29",
        };
        this.setAdapter(new ArrayAdapter<>(this.getContext(), android.R.layout.simple_list_item_1, strings));
        this.setOnScrollListener(this);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        System.out.println(firstVisibleItem + " " + visibleItemCount + " " + totalItemCount);
        if (firstVisibleItem == 0 && this.getParent() != null) {
            ((ContentViewPager) this.getParent()).setCanDownScroll(true);
        } else if (this.getParent() != null) {
            ((ContentViewPager) this.getParent()).setCanDownScroll(false);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this.getContext(), "" + position, Toast.LENGTH_SHORT).show();
    }
}
