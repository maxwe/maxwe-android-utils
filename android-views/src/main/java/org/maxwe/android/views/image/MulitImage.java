package org.maxwe.android.views.image;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by dingpengwei on 2/5/15.
 * 本地多图片展示
 */
public class MulitImage extends RelativeLayout{

    private static enum BY{
        index,path
    }

    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {

        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };

    private PagerAdapter adapter = new PagerAdapter(){

        @Override
        public int getCount() {
            if (BY.index == by){
                return imagesByIndex.size();
            }else if (BY.path == by){
                return imagesByPath.size();
            }else {
                return 0;
            }
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

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
        public boolean isViewFromObject(View view, Object o) {
            return view == o;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            position %= pageViews.size();
            if (position < 0) {
                position = pageViews.size() + position;
            }

            ImageView pageView = pageViews.get(position);
            pageView.setScaleType(ImageView.ScaleType.FIT_XY);
            ViewParent parent = pageView.getParent();
            if (parent != null) {
                ViewGroup viewGroup = (ViewGroup) parent;
                viewGroup.removeView(pageView);
            }
            pageView.setImageResource(imagesByIndex.get(position));
            container.addView(pageView);
            System.out.println(imagesByIndex.get(position));
            return pageView;
        }
    };

    private Context context;
    private ViewPager viewPager = null;
    private List<Integer> imagesByIndex;
    private List<String> imagesByPath;
    private BY by;

    List<ImageView> pageViews = new LinkedList<>();

    public MulitImage(Context context) {
        super(context);
        this.init(context);
    }

    public MulitImage(Context context,List<Integer> imagesByIndex) {
        super(context);
        this.by = BY.index;
        this.imagesByIndex = imagesByIndex;
        this.init(context);
    }

    public MulitImage(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init(context);
    }

    public MulitImage(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.init(context);
    }

    private void init(Context context){
        this.context = context;
        ImageView imageView1 = new ImageView(context);
        imageView1.setImageResource(imagesByIndex.get(0));
        this.pageViews.add(imageView1);

        ImageView imageView2 = new ImageView(context);
        imageView2.setImageResource(imagesByIndex.get(1));
        this.pageViews.add(imageView2);

        ImageView imageView3 = new ImageView(context);
        imageView2.setImageResource(imagesByIndex.get(2));
        this.pageViews.add(imageView3);

        this.viewPager = new ViewPager(context);
        this.addView(this.viewPager);
        this.viewPager.setAdapter(this.adapter);
        this.viewPager.setOnPageChangeListener(this.onPageChangeListener);
    }

    public void setImagesByIndex(List<Integer> imagesByIndex) {
        this.by = BY.index;
        this.imagesByIndex = imagesByIndex;
        this.adapter.notifyDataSetChanged();
    }

    public void setImagesByPath(List<String> imagesByPath) {
        this.by = BY.path;
        this.imagesByPath = imagesByPath;
        this.adapter.notifyDataSetChanged();
    }
}
