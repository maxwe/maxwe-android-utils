package org.maxwe.android.utils.views.tablist;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

/**
 * Created by Pengwei Ding on 2016-01-11 21:47.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class ContentContainer extends FrameLayout {
    public ContentContainer(Context context) {
        super(context);
    }

    public ContentContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ContentContainer(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

}
