package com.laomu.note.common.screenshot.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.laomu.note.R;
import com.laomu.note.ui.NoteApplication;

import java.util.ArrayList;
import java.util.List;

public class ScreenshotView extends TextView {
    private Bitmap bitmapBackgroud;
    private Bitmap bitmapDoodle;
    private Bitmap bitmapSeal;

    private Canvas mCanvas;
    private Path mPath;
    private Paint mDoodlePaint;
    private float mX, mY;// 临时点坐标
    private static final float TOUCH_TOLERANCE = 4;

    private String mSealText = "轻触编辑文案";
    private Paint mSealPaint;

    // 保存Path路径的集合,用List集合来模拟栈
    private static List<DrawPath> savePath;
    // 记录Path路径的对象
    private DrawPath dp;

    private boolean isInit = false;// 用来标记保证只被初始化一次

    // 布局高和宽
    private int screenWidth = 500, screenHeight = 500;

    public ScreenshotView(Context context) {
        super(context);
    }

    public ScreenshotView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScreenshotView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void init(int w, int h) {
        // 保证只被初始化一次就够了
        if (!isInit) {
            screenWidth = w;
            screenHeight = h;

            bitmapBackgroud = Bitmap.createBitmap(screenWidth, screenHeight,
                    Bitmap.Config.ARGB_8888);

            // 保存一次一次绘制出来的图形
            mCanvas = new Canvas(bitmapBackgroud);

            mDoodlePaint = new Paint();
            mDoodlePaint.setAntiAlias(true);
            mDoodlePaint.setStyle(Paint.Style.STROKE);
            mDoodlePaint.setStrokeJoin(Paint.Join.ROUND);// 设置外边缘
            mDoodlePaint.setStrokeCap(Paint.Cap.ROUND);// 形状
            mDoodlePaint.setStrokeWidth(5);// 画笔宽度

            savePath = new ArrayList<DrawPath>();
            isInit = true;
            // 保持一个path,支持多次手势涂鸦重叠
            mPath = new Path();
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
//		int color = getResources().getColor(R.color.white);
//		canvas.drawColor(color);

        Bitmap bmp = BitmapFactory.decodeResource(NoteApplication.appContext.getResources(), R.drawable.ic_launcher);

        if (bitmapBackgroud == null) {
            canvas.drawBitmap(bmp, 0, 0, null);
        } else {
            canvas.drawBitmap(bitmapBackgroud, 0, 0, null);
        }

        // 将前面已经画过得显示出来
//        canvas.drawBitmap(bmp, 0, 0, null);
        if (mPath != null) {
            // 实时的显示
            canvas.drawPath(mPath, mDoodlePaint);
        }
    }

    // 布局的大小改变时，就会调用该方法
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        // 布局的大小改变时，就会调用该方法，在这里获取到view的高和宽
        screenWidth = w;
        screenHeight = h;

        // view初始化
        init(screenWidth, screenHeight);
        super.onSizeChanged(w, h, oldw, oldh);
    }

    /**
     * 撤销上一步画线<br />
     * 撤销的核心思想就是将画布清空，将保存下来的Path路径最后一个移除掉，重新将路径画在画布上面。
     */
    public void undo() {
        if (savePath != null && savePath.size() > 0) {
            savePath.remove(savePath.size() - 1);
            redrawOnBitmap();
        }
    }

    /**
     * 重做,就是清空所有的画线<br />
     * 核心思想就是，清空Path路径后，进行重新绘制
     */
    public void redo() {
        if (savePath != null && savePath.size() > 0) {
            savePath.clear();
            redrawOnBitmap();
        }
    }

    // 按下
    private void touch_start(float x, float y) {
        mPath.moveTo(x, y);
        mX = x;
        mY = y;
    }

    // 正在滑动中
    private void touch_move(float x, float y) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(mY - y);
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            // 从（mX,mY）到（x,y）画一条贝塞尔曲线，更平滑(直接用mPath.lineTo也是可以的)
            mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
            mX = x;
            mY = y;
        }
    }

    // 滑动太手
    private void touch_up() {
        mPath.lineTo(mX, mY);
        mCanvas.drawPath(mPath, mDoodlePaint);
        // 将一条完整的路径保存下来(相当于入栈操作)
        savePath.add(dp);
//		mPath = null;// 重新置空
    }

    // 重新绘制Path中的画线路径
    private void redrawOnBitmap() {
        bitmapBackgroud = Bitmap.createBitmap(screenWidth, screenHeight,
                Bitmap.Config.ARGB_8888);
        mCanvas.setBitmap(bitmapBackgroud);// 重新设置画布，相当于清空画布

        for (DrawPath drawPath : savePath) {
            mCanvas.drawPath(drawPath.path, drawPath.paint);
        }

        invalidate();// 刷新
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 每一次记录的路径对象是不一样的
                dp = new DrawPath();
                dp.path = mPath;
                dp.paint = mDoodlePaint;
                touch_start(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                touch_move(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                touch_up();
                invalidate();
                break;
        }

        return true;
    }

    public void setImageBackgroud(Bitmap bitmap) {
        bitmapBackgroud = bitmap;
        invalidate();
    }

    private class DrawPath {
        public Path path;// 路径
        public Paint paint;// 画笔
    }

}
