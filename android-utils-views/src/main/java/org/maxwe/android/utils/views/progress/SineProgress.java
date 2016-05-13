package org.maxwe.android.utils.views.progress;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Pengwei Ding on 2016-04-12 09:14.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * 正弦型函数解析式：y = Asin（ωx+φ）+ h
 * 各常数值对函数图像的影响：
 * φ（初相位）：决定波形与X轴位置关系或横向移动距离（左加右减）
 * ω：决定周期（最小正周期T=2π/|ω|）
 * A：决定峰值（即纵向拉伸压缩的倍数）
 * h：表示波形在Y轴的位置关系或纵向移动距离（上加下减）
 * 作图方法运用“五点法”作图
 * “五点作图法”即当ωx+φ分别取0，π/2，π，3π/2，2π时y的值.
 */
public class SineProgress extends SurfaceView implements SurfaceHolder.Callback {
    private Path path = new Path();
    private Paint paint = new Paint();
    private boolean drawing = false;

    public SineProgress(Context context) {
        super(context);
        this.init();
    }

    public SineProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init();
    }

    public SineProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init();
    }

    private void init() {
        paint.setStrokeWidth(0);
        paint.setColor(Color.GREEN);
        this.path.reset();
        this.getHolder().addCallback(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    public void surfaceCreated(final SurfaceHolder holder) {
        drawing = true;
//        * 正弦型函数解析式：y = Asin（ωx+φ）+ h
//        * φ（初相位）：决定波形与X轴位置关系或横向移动距离（左加右减）
//        * ω：决定周期（最小正周期T=2π/|ω|）
//        * A：决定峰值（即纵向拉伸压缩的倍数）
//        * h：表示波形在Y轴的位置关系或纵向移动距离（上加下减）
        final int width = this.getWidth();
        final int height = this.getHeight();
        final int A = 30;
        final double ω = 0.5 * 3.14 / 180;
        final int φ = 1;//在X轴上移动的偏移量
        final int h = height / 2;//在Y轴上移动的偏移量

        new Thread() {
            public void run() {
                int offset = φ;
                while (drawing) {
                    offset ++;
                    if (holder == null){
                        break;
                    }
                    Canvas canvas = holder.lockCanvas(new Rect(0, 0, width, height));
                    if (canvas == null){
                        break;
                    }
                    canvas.drawColor(Color.BLACK);
                    paint.setColor(Color.rgb((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255)));
//                    canvas.drawLine(0, height / 2, width, height / 2, paint);
                    for (int x = 0; x < width; x = x + 2) {
                        int y1 = (int) (A * Math.sin(ω * x + offset)) + h;
                        int y2 = (int) (A * Math.sin(ω * x + offset - 5)) + h;
                        canvas.drawPoint(x, y1, paint);
                        canvas.drawPoint(x, y2, paint);
                    }
                    holder.unlockCanvasAndPost(canvas);
                    try {
                        Thread.sleep(40);
                    } catch (Exception e) {

                    }
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
}
