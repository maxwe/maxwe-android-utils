package org.maxwe.android.utils.views.progress;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Pengwei Ding on 2016-05-05 09:55.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class LoadingProgress extends SurfaceView implements SurfaceHolder.Callback {
    private int width;
    private int height;

    private Path path = new Path();
    private Paint paint = new Paint();

    private boolean drawing = false;

    private SurfaceHolder surfaceHolder;

    public LoadingProgress(Context context) {
        super(context);
        this.init();
    }

    public LoadingProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init();
    }

    public LoadingProgress(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.init();
    }

    private void init() {
        this.paint.setColor(Color.WHITE);
        this.paint.setStrokeWidth(5);
        this.surfaceHolder = this.getHolder();
        this.surfaceHolder.addCallback(this);
    }

    @Override
    public void surfaceCreated(final SurfaceHolder holder) {
        drawing = true;
        new Thread() {
            public void run() {
                while (drawing) {
                    drawText(holder);
                }
            }
        }.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        drawing = false;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.width = MeasureSpec.getSize(widthMeasureSpec);
        this.height = MeasureSpec.getSize(heightMeasureSpec);
        this.currentX = halfWidth = this.width / 2;
        this.currentY = marginTop;

        points = new float[this.height * 80];
    }

    private float[] points;
    int halfWidth;
    private int wordWidth = 50;
    private int currentX, currentY;
    private int marginBottom = 50, marginTop = 50;
    private int index;
    private int deleteIndex;

    private void drawText(SurfaceHolder holder) {
        Canvas canvas = holder.lockCanvas();
        if (canvas == null) {
            return;
        }
        canvas.drawColor(Color.BLACK);
        if (currentX == halfWidth && currentY == marginTop) {
            /**
             * 零号拐点
             */
            points[index * 2] = currentX;
            points[index * 2 + 1] = currentY;
            currentY++;
        } else if (currentX == halfWidth && currentY < height - marginBottom) {
            points[index * 2] = currentX;
            points[index * 2 + 1] = currentY;
            currentY++;
        } else if (currentX == halfWidth && currentY == height - marginBottom) {
            /**
             * 一号拐点
             */
            points[index * 2] = currentX;
            points[index * 2 + 1] = currentY;
            currentX++;
        } else if (currentX > halfWidth && currentX < halfWidth + wordWidth && currentY == height - marginBottom) {
            points[index * 2] = currentX;
            points[index * 2 + 1] = currentY;
            currentX++;
        } else if (currentX == halfWidth + wordWidth && currentY == height - marginBottom) {
            /**
             * 二号拐点
             */
            points[index * 2] = currentX;
            points[index * 2 + 1] = currentY;
            currentY--;
        } else if (currentX == halfWidth + wordWidth && currentY < height - marginBottom && currentY > marginTop) {
            points[index * 2] = currentX;
            points[index * 2 + 1] = currentY;
            currentY--;
        } else if (currentX == halfWidth + wordWidth && currentX == marginTop) {
            /**
             * 三号拐点
             */
            points[index * 2] = currentX;
            points[index * 2 + 1] = currentY;
            currentX--;
        } else if (currentX > halfWidth && currentY == marginTop) {
            points[index * 2] = currentX;
            points[index * 2 + 1] = currentY;
            currentX--;
        }

        index++;

        if (index > height) {
            canvas.drawPoints(points, deleteIndex * 2, this.points.length - deleteIndex * 2, this.paint);
            deleteIndex++;
        } else {
            canvas.drawPoints(points, this.paint);
        }

        if (currentY >= this.points.length / 2) {
            drawing = false;
        }
        holder.unlockCanvasAndPost(canvas);
    }
}
