package org.maxwe.android.utils.views.editer;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;

/**
 * Created by Pengwei Ding on 2016-04-06 17:03.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class EditorContainer extends ScrollView {
    public EditorContainer(Context context) {
        super(context);
        this.init();
    }

    public EditorContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init();
    }

    public EditorContainer(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.init();
    }

    private void init(){
        LinearLayout linearLayout = new LinearLayout(this.getContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.addView(new EditText(this.getContext()));
        linearLayout.addView(new EditText(this.getContext()));
        this.addView(linearLayout);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        this.getChildAt(0).layout(0, 0, r - l, b - t);
        ((ViewGroup)this.getChildAt(0)).getChildAt(0).layout(0, 0, r - l, (b - t) / 2);
        ((ViewGroup)this.getChildAt(0)).getChildAt(1).layout(0, (b - t) / 2, r - l, b - t);
    }
}
