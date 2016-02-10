package org.maxwe.android.utils.sample.tablist;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import org.maxwe.android.utils.views.tablist.IContent;

/**
 * Created by Pengwei Ding on 2016-02-10 09:50.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class ContentLinearLayout extends LinearLayout  implements IContent {
    public ContentLinearLayout(Context context) {
        super(context);
        this.init();
    }

    public ContentLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init();
    }

    public ContentLinearLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.init();
    }

    private void init(){
        this.setOrientation(LinearLayout.VERTICAL);
        ContentViewPager contentViewPager = new ContentViewPager(this.getContext());
        this.addView(contentViewPager);
    }
}
