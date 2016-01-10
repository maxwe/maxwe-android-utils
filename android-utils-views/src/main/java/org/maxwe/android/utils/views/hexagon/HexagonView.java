package org.maxwe.android.utils.views.hexagon;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import java.util.LinkedList;

/**
 * Created by Pengwei Ding on 2016-01-08 15:54.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class HexagonView extends RelativeLayout{
    private float sideLength;
    private Paint paint = new Paint();

    public HexagonView(Context context) {
        super(context);
        this.initView();
    }

    public HexagonView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.initView();
    }

    public HexagonView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.initView();
    }

    private void initView(){
        this.setWillNotDraw(false);
        this.paint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.drawCircle(this.sideLength / 2, this.sideLength / 2, this.sideLength / 2, this.paint);
//        canvas.drawLines(getHexagonPointsToFloats(), this.paint);

//        Matrix matrix = getMatrix();
//        matrix.setScale(0.55f,0.55f);
//        canvas.drawBitmap(BitmapFactory.decodeResource(this.getContext().getResources(), R.drawable.index_01), matrix, this.paint);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        this.sideLength = r - l;
        this.invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        float eventX = event.getX();
        float eventY = event.getY();
        if (MotionEvent.ACTION_DOWN == action){
            LinkedList<Point> hexagonPoints = this.getHexagonPoints();
            Point point0 = hexagonPoints.get(0);
            Point point3 = hexagonPoints.get(3);
            if (eventX > point3.getX() && eventX < point0.getX() && eventY > point0.getY() && eventY < point3.getY()) {
                /**
                 * 由象限中的四个点连接而成的矩形范围
                 */
                return super.onTouchEvent(event);
            }
            return false;
        }
        return super.onTouchEvent(event);
    }


    private LinkedList<Point> getHexagonPoints() {
        LinkedList<Point> points = new LinkedList<>();
        float sideLength = this.sideLength;
        float radius = sideLength / 2;
        float halfRadius = radius / 2;
        float cos30OfRadius = (float) (radius * Math.cos(Math.PI / 6));
        Point point0 = new Point(radius + cos30OfRadius, halfRadius);
        Point point1 = new Point(radius, 0);
        Point point2 = new Point(radius - cos30OfRadius, halfRadius);
        Point point3 = new Point(radius - cos30OfRadius, radius + halfRadius);
        Point point4 = new Point(radius, sideLength);
        Point point5 = new Point(radius + cos30OfRadius, radius + halfRadius);
        points.add(point0);
        points.add(point1);
        points.add(point2);
        points.add(point3);
        points.add(point4);
        points.add(point5);
        return points;
    }

    private float[] getHexagonPointsToFloats() {
        LinkedList<Point> hexagonPoints = this.getHexagonPoints();
        float[] floats = new float[4 * 6];
        int indexCounter = 0;
        for (int index = 0; index < hexagonPoints.size(); index++) {
            Point point = hexagonPoints.get(index);
            floats[indexCounter++] = point.getX();
            floats[indexCounter++] = point.getY();
            Point nextPoint;
            if (index == hexagonPoints.size() - 1) {
                nextPoint = hexagonPoints.get(0);
            } else {
                nextPoint = hexagonPoints.get(index + 1);
            }
            floats[indexCounter++] = nextPoint.getX();
            floats[indexCounter++] = nextPoint.getY();
        }
        return floats;
    }

    private class Point {
        private float x;
        private float y;

        public Point(float x, float y) {
            this.x = x;
            this.y = y;
        }

        public float getX() {
            return x;
        }

        public void setX(float x) {
            this.x = x;
        }

        public float getY() {
            return y;
        }

        public void setY(float y) {
            this.y = y;
        }
    }
}
