package org.maxwe.android.utils.views.tablist;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Pengwei Ding on 2016-01-11 21:47.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description:
 * 一个容器
 * 包含了一个标题区域
 * 包含了一个内容区域
 * 上下滑动引起标题的显示或隐藏
 */
public class Container extends ViewGroup {

    private View titleView;
    private View contentView;
    private int titleViewOriginHeight;
    private int titleViewCurrentBottomBaseLine = -1;
    private boolean canScroll = true;
    private boolean canDownScroll = true;

    public Container(Context context) {
        super(context);
    }

    public Container(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Container(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private Pointer previousPointer = new Pointer();

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        Pointer currentPointer = new Pointer(ev.getRawX(), ev.getRawY());
        if (MotionEvent.ACTION_DOWN == action) {
            this.previousPointer = new Pointer(currentPointer.getX(), currentPointer.getY());
        } else if (MotionEvent.ACTION_MOVE == action) {
            if (this.titleViewCurrentBottomBaseLine < this.titleViewOriginHeight && this.titleViewCurrentBottomBaseLine > 0) {
                /**
                 * 此时向上向下均处理
                 */
                if (this.previousPointer.in45(currentPointer) && this.isCanScroll()) {
                    this.resetLayout(currentPointer.getY());
                    this.previousPointer.setTo(currentPointer);
                    return false;
                } else {
                    this.previousPointer.setTo(currentPointer);
                    return super.dispatchTouchEvent(ev);
                }
            } else if (this.titleViewCurrentBottomBaseLine <= 0) {
                /**
                 * 此时向下处理
                 */
                if (currentPointer.getY() >= this.previousPointer.getY()) {
                    if (this.previousPointer.in45(currentPointer) && this.isCanScroll() && this.isCanDownScroll()) {
//                        System.out.println("触发向下处理-" + this.previousPointer + " " + currentPointer);
                        this.resetLayout(currentPointer.getY());
                        this.previousPointer.setTo(currentPointer);
                        return false;
                    } else {
                        this.previousPointer.setTo(currentPointer);
                        return super.dispatchTouchEvent(ev);
                    }
                }
                this.previousPointer.setTo(currentPointer);
                return this.contentView.dispatchTouchEvent(ev);
            } else if (this.titleViewCurrentBottomBaseLine >= this.titleViewOriginHeight) {
                /**
                 * 此时向上处理
                 */
                if (currentPointer.getY() <= this.previousPointer.getY()) {

                    if (this.previousPointer.in45(currentPointer) && this.isCanScroll()) {
//                        System.out.println("触发向上处理-" + this.previousPointer + " " + currentPointer);
                        this.resetLayout(currentPointer.getY());
                        this.previousPointer.setTo(currentPointer);
                        return false;
                    } else {
                        this.previousPointer.setTo(currentPointer);
                        return super.dispatchTouchEvent(ev);
                    }
                }
                this.previousPointer.setTo(currentPointer);
                return super.dispatchTouchEvent(ev);
            } else {
                /**
                 * 其余情况均不处理
                 */
                this.previousPointer.setTo(currentPointer);
                return super.dispatchTouchEvent(ev);
            }
        } else if (MotionEvent.ACTION_UP == action) {

        }
        this.previousPointer.setTo(currentPointer);
        return super.dispatchTouchEvent(ev);
    }


    private void resetLayout(float currentTouchY) {
        this.titleViewCurrentBottomBaseLine = this.titleViewCurrentBottomBaseLine + (int) (currentTouchY - this.previousPointer.getY());
        this.titleViewCurrentBottomBaseLine = this.titleViewCurrentBottomBaseLine > this.titleViewOriginHeight ? this.titleViewOriginHeight : this.titleViewCurrentBottomBaseLine;
        this.titleViewCurrentBottomBaseLine = this.titleViewCurrentBottomBaseLine < 0 ? 0 : this.titleViewCurrentBottomBaseLine;

        this.titleView.layout(this.l, this.t + this.titleViewCurrentBottomBaseLine - this.titleViewOriginHeight, this.r, this.titleViewCurrentBottomBaseLine);
        this.contentView.layout(this.l, this.t + this.titleViewCurrentBottomBaseLine, this.r, this.b);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    private int l;
    private int t;
    private int r;
    private int b;

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed) {
            this.l = l;
            this.t = t;
            this.r = r;
            this.b = b;
            this.titleView.layout(this.l, this.t, this.r, this.titleViewCurrentBottomBaseLine);
            this.contentView.layout(this.l, this.t + this.titleViewCurrentBottomBaseLine, this.r, this.b);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        this.titleView = this.getChildAt(0);
        this.contentView = this.getChildAt(1);

        if (this.titleView == null || !(this.titleView instanceof ITitle)) {
            throw new NullPointerException("标题容器不能为空并且类型是：" + ITitle.class.getName());
        }
        if (this.contentView == null || !(this.contentView instanceof IContent)) {
            throw new NullPointerException("内容容器不能为空并且类型是：" + IContent.class.getName());
        }

        LayoutParams layoutParamsOfTitle = this.titleView.getLayoutParams();
        this.titleView.measure(MeasureSpec.makeMeasureSpec(layoutParamsOfTitle.width, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(layoutParamsOfTitle.height, MeasureSpec.EXACTLY));

        LayoutParams layoutParamsOfContent = this.contentView.getLayoutParams();
        this.contentView.measure(MeasureSpec.makeMeasureSpec(layoutParamsOfContent.width, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(layoutParamsOfContent.height, MeasureSpec.EXACTLY));

        this.titleViewOriginHeight = this.titleView.getMeasuredHeight();

        if (this.titleViewCurrentBottomBaseLine == -1){
            this.titleViewCurrentBottomBaseLine = this.titleViewOriginHeight;
        }

        this.setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec));
        this.measureChildren(widthMeasureSpec, heightMeasureSpec);
    }

    public boolean isCanScroll() {
        return canScroll;
    }

    public void setCanScroll(boolean canScroll) {
        this.canScroll = canScroll;
    }

    public boolean isCanDownScroll() {
        return canDownScroll;
    }

    public void setCanDownScroll(boolean canDownScroll) {
        this.canDownScroll = canDownScroll;
    }

    private class Pointer {
        private float x;
        private float y;

        public Pointer() {
            super();
        }

        public Pointer(float x, float y) {
            this.x = x;
            this.y = y;
        }

        public Pointer(Pointer pointer) {
            this.x = pointer.getX();
            this.y = pointer.getY();
        }

        public float getX() {
            return x;
        }

        public Pointer setX(float x) {
            this.x = x;
            return this;
        }

        public float getY() {
            return y;
        }

        public Pointer setY(float y) {
            this.y = y;
            return this;
        }

        public Pointer setTo(Pointer pointer) {
            this.x = pointer.getX();
            this.y = pointer.getY();
            return this;
        }

        public boolean in45(Pointer pointer) {
            if (Math.abs(this.getX() - pointer.getX()) <= Math.abs(this.getY() - pointer.getY())) {
                //纵向
                return true;
            } else {

                //横向
                return false;
            }
        }

        @Override
        public String toString() {
            return "x = " + this.getX() + "; y = " + this.getY();
        }
    }
}
