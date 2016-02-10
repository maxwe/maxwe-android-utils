package org.maxwe.android.utils.sample.tablist;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import org.maxwe.android.utils.views.tablist.Container;
import org.maxwe.android.utils.views.tablist.IContent;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Pengwei Ding on 2016-02-05 15:55.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class ContentViewPager extends ViewPager implements IContent {

    public ContentViewPager(Context context) {
        super(context);
        this.init();
    }

    public ContentViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init();
    }

    private List<PageItem> pageItems = new LinkedList<>();

    private void init() {
        pageItems.add(new PageItem(this.getContext()));
        pageItems.add(new PageItem(this.getContext()));
        pageItems.add(new PageItem(this.getContext()));

        this.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return pageItems.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public int getItemPosition(Object object) {
                int itemPosition = this.getItemPosition(object);
                if (itemPosition < 0) {
                    return itemPosition;
                }
                return super.getItemPosition(object);
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                PageItem pageView = pageItems.get(position);
                ViewParent parent = pageView.getParent();
                if (parent != null) {
                    ViewGroup viewGroup = (ViewGroup) parent;
                    viewGroup.removeView(pageView);
                }
                container.addView(pageView);
                return pageItems.get(position);
            }
        });
    }

    private Pointer previousPointer = new Pointer();

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        Pointer currentPointer = new Pointer(ev.getRawX(), ev.getRawY());
        if (MotionEvent.ACTION_DOWN == action) {
            this.previousPointer = new Pointer(currentPointer.getX(), currentPointer.getY());
        } else if (MotionEvent.ACTION_MOVE == action) {
            if (this.previousPointer.in45(currentPointer)) {
                return false;
            } else {
                return super.onTouchEvent(ev);
            }
        }
        return super.onTouchEvent(ev);
    }

    public void setCanDownScroll(boolean canDownScroll) {
        ((Container) this.getParent()).setCanDownScroll(canDownScroll);
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
