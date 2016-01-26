package org.maxwe.android.utils.views.hexagon;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import org.maxwe.android.utils.views.R;

import java.util.LinkedList;

/**
 * Created by Pengwei Ding on 2016-01-08 15:53.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class HexagonContainer extends RelativeLayout {
    private LinkedList<HexagonView> hexagonViews;
    private int containerWidth;
    private int containerHeight;
    private int column;

    public HexagonContainer(Context context, LinkedList<HexagonView> hexagonViews, int column) {
        super(context);
        this.hexagonViews = hexagonViews;
        this.column = column;
        for (HexagonView hexagonView : hexagonViews) {
            this.addView(hexagonView);
        }
    }

    public HexagonContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.initAttr(attrs);
    }

    public HexagonContainer(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.initAttr(attrs);
    }

    private void initAttr(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.HexagonContainer);
        this.column = typedArray.getInteger(R.styleable.HexagonContainer_column, 1);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.containerWidth = MeasureSpec.getSize(widthMeasureSpec);
        this.containerHeight = MeasureSpec.getSize(heightMeasureSpec);
//        this.setMeasuredDimension(this.containerWidth,this.containerHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed,l,t,r,b);
        int childCount = this.getChildCount();
        float sideLength = this.containerWidth / this.column;
//        float leftOffset = (sideLength / 2 - (float) (sideLength / 2 * Math.cos(Math.PI / 6))) / 2;
        float leftOffset = 0.0f;
        float topOffset = sideLength / 2 / 3;

        float startX = 0;
        float startY = 0;

        for (int index = 0; index < childCount; index++) {
            HexagonView childAt = (HexagonView) this.getChildAt(index);
            childAt.measure(MeasureSpec.makeMeasureSpec((int)sideLength,MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec((int)sideLength,MeasureSpec.EXACTLY));
            childAt.layout((int) startX, (int) startY, (int) (startX + sideLength), (int) (startY + sideLength));
            System.out.println();
            if (startX + sideLength * 2 > this.containerWidth) {
                startY += sideLength - topOffset;

                if (((index + 1) / (this.column * 2 - 1)) * (this.column * 2 - 1) + this.column == index + 1) {
                    startX = sideLength / 2 - leftOffset;
                } else {
                    startX = 0;
                }
            } else {
                startX += sideLength;
            }
        }
    }
}
