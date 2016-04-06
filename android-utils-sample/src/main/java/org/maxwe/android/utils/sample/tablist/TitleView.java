package org.maxwe.android.utils.sample.tablist;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import org.maxwe.android.utils.sample.R;
import org.maxwe.android.utils.views.tablist.ITitle;

/**
 * Created by Pengwei Ding on 2016-02-05 16:02.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class TitleView extends RelativeLayout implements ITitle {
    public TitleView(Context context) {
        super(context);
        this.init();
    }

    public TitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init();
    }

    public TitleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.init();
    }

    private void init(){
        ImageView imageView = new ImageView(this.getContext());
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        layoutParams.addRule(CENTER_IN_PARENT);
        imageView.setLayoutParams(layoutParams);
        imageView.setImageResource(R.drawable.ic_launcher);
        this.addView(imageView);
    }
}
