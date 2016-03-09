package com.laomu.note.module.share.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

/**
 * Created by ${yipengmu} on 16/3/9.
 *
 * 用于颜色选择器
 */
public class LinearColorSelectorView extends ImageView {
    private float mLastedX;
    private float mLastedY;

    Paint mColorcyclePaint;
    Paint mGradientPaint;
    //显示卡圆片半径
    private int cycleDadill = 40;
    private int mLiearGradientStartLeft;
    private int mLiearGradientStartRight;
    private Bitmap currentBitmap;
    boolean bGetDrawingCacheBitmap = false;

    public LinearColorSelectorView(Context context) {
        super(context);
        init();
    }

    public LinearColorSelectorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mColorcyclePaint = new Paint();

        mColorcyclePaint.setColor(Color.RED);
        mColorcyclePaint.setStyle(Paint.Style.FILL);
        mColorcyclePaint.setTextSize(10);
        mColorcyclePaint.setAntiAlias(true);

        mGradientPaint = new Paint();

        this.setDrawingCacheEnabled(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        LinearGradient gradient = new LinearGradient(0, 0, 0, getMeasuredHeight(), new int[]{
                Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW}, null,
                Shader.TileMode.REPEAT);
        mGradientPaint.setShader(gradient);

        mLiearGradientStartLeft = (cycleDadill * 2) + 20;
        mLiearGradientStartRight = 150;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        currentBitmap = getDrawingCache();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //调色板
        canvas.drawRect(mLiearGradientStartLeft, 0, mLiearGradientStartRight, getMeasuredHeight(), mGradientPaint);

        //颜色展示圆片
        canvas.drawCircle(cycleDadill, mLastedY, cycleDadill, mColorcyclePaint);

    }

    private void checkDrawingCacheBitmap() {
        if (!bGetDrawingCacheBitmap) {
            currentBitmap = getDrawingCache();

            bGetDrawingCacheBitmap = true;
        }

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float x = event.getX();
        float y = event.getY();

        mLastedX = x;
        mLastedY = y;

        checkDrawingCacheBitmap();
        updateColorcyclePaint((int) mLastedX, (int) mLastedY);

        invalidate();
        return true;
    }

    private void updateColorcyclePaint(int xx, int yy) {
        if (xx <= currentBitmap.getWidth() && yy <= currentBitmap.getHeight() && xx >= mLiearGradientStartLeft && xx <= mLiearGradientStartRight) {
            mColorcyclePaint.setColor(currentBitmap.getPixel(xx, yy));
        } else {
            mColorcyclePaint.setColor(Color.TRANSPARENT);
        }
    }

    @Override
    protected void onDetachedFromWindow() {

        this.setDrawingCacheEnabled(false);

        super.onDetachedFromWindow();

    }
}
