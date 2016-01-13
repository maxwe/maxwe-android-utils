package org.maxwe.android.utils.views.tablist;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by Pengwei Ding on 2016-01-11 12:11.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class TabListContainer extends LinearLayout {

    private TitleContainer titleContainer;
    private ContentContainer contentContainer;

    public TabListContainer(Context context) {
        super(context);
        this.init();
    }

    public TabListContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init();
    }

    public TabListContainer(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.init();
    }

    private void init() {
        this.setOrientation(LinearLayout.VERTICAL);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        View childAt0 = this.getChildAt(0);
        View childAt1 = this.getChildAt(1);
        if (childAt0 == null || !(childAt0 instanceof TitleContainer)){
            new Throwable("标题容器不能为空并且类型是：" + TitleContainer.class.getName());
            return;
        }
        if (childAt1 == null || !(childAt1 instanceof ContentContainer)){
            new Throwable("内容容器不能为空并且类型是：" + ContentContainer.class.getName());
            return;
        }
        this.titleContainer = (TitleContainer)childAt0;
        this.contentContainer = (ContentContainer)childAt1;
    }

    private float preTouchMoveY;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        float currentTouchMoveY = ev.getRawY();
        if (MotionEvent.ACTION_DOWN == action) {
            preTouchMoveY = currentTouchMoveY;
        } else if (MotionEvent.ACTION_MOVE == action) {
            if (currentTouchMoveY > this.preTouchMoveY){
                /**
                 * 手指向下移动
                 */
            }else{
                /**
                 * 手指向上移动
                 */
            }
            int titleContainerHeight = this.titleContainer.getHeight();
            if (titleContainerHeight > 0) {
                this.titleContainer.layout(0, 0, this.getWidth(), (int) (titleContainerHeight - (preTouchMoveY - currentTouchMoveY)));
                this.contentContainer.layout(0,titleContainerHeight,this.getWidth(),this.getHeight());
                this.preTouchMoveY = ev.getRawY();
                return false;
            }
            int contentContainerHeight = this.contentContainer.getHeight();
            if (contentContainerHeight == this.getHeight()){
                this.titleContainer.layout(0, 0, this.getWidth(), (int) (titleContainerHeight - (preTouchMoveY - currentTouchMoveY)));
                this.contentContainer.layout(0,titleContainerHeight,this.getWidth(),this.getHeight());
                this.preTouchMoveY = ev.getRawY();
                return false;
            }

            return super.dispatchTouchEvent(ev);

        } else if (MotionEvent.ACTION_UP == action) {
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        if (MotionEvent.ACTION_MOVE == action) {
            System.out.println();
        }
        return true;
    }
}
