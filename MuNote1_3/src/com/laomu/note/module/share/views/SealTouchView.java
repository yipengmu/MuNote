package com.laomu.note.module.share.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.laomu.note.common.MuLog;
import com.laomu.note.common.screenshot.ScreenshotManager;
import com.laomu.note.common.screenshot.view.SealTextClickListener;

/**
 * Created by ${yipengmu} on 16/3/7.
 */
public class SealTouchView extends ImageView {

    private Paint mSealPaint;

    // 布局高和宽
    private SealRectHolder mHolder;
    private SealTextClickListener mSealTextClickListener;

    public SealTouchView(Context context) {
        super(context);

        init();
    }

    public SealTouchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SealTouchView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public void init() {
        mHolder = new SealRectHolder();
        mSealPaint = new Paint();
        mSealPaint.setColor(Color.RED);
        mSealPaint.setStyle(Paint.Style.FILL);
        mSealPaint.setTextSize(40);
        mSealPaint.setAntiAlias(true);


        mHolder.bitmapSeal = ScreenshotManager.getEditTextUIBitmap();

        mHolder = new SealRectHolder();


    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mHolder.setContainerLayout(getWidth(),getHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        MuLog.logd("onDraw SealTouchView = " + getWidth() + " " + getHeight());
        MuLog.logd("onDraw bitmapSeal = " + mHolder.bitmapSeal.getWidth() + " " + mHolder.bitmapSeal.getHeight());
        MuLog.logd("onDraw bitmapSeal RatateIcon= " + mHolder.getRatateIconX() + " " + mHolder.getRatateIconY());

        if (mHolder.bitmapSeal != null) {
//            canvas.drawBitmap(mHolder.bitmapSeal, mHolder.getSealX(getWidth()), mHolder.getSealY(getHeight()), null);

            canvas.drawBitmap(mHolder.bitmapSeal,0 ,0, null);

            //绘制 删除x 和 旋转箭头 按钮
//            canvas.drawBitmap(mHolder.bitmapDel, mHolder.getDelateIconX(), mHolder.getDelateIconY(), null);
//
//            canvas.drawBitmap(mHolder.bitmapRotate, mHolder.getRatateIconX(), mHolder.getRatateIconY(), null);
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchDown(x, y);
//                checkSealTextClick(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                touchMove(x, y);
                break;
            case MotionEvent.ACTION_UP:
//                touch_up();
                break;
        }
        MuLog.logd("onTouchEvent event.getX()=" + x+ " event.getY()=" + y);

        invalidate();
        return true;
    }

    private void touchMove(float x, float y) {

    }

    private void touchDown(float x, float y) {

    }

    private void checkSealTextClick(float x, float y) {
        if (mSealTextClickListener == null) {
            return;
        }

        //判断当前按下触发的是否是落在编辑框区域中
        if (x > 0 && x < mHolder.getSealWidth() && (y > 0 && y < mHolder.getSealHeight())) {

            MuLog.logd("onTouchEvent onTextRectInSideClick" );
            //如果在区域内，则进入此逻辑：隐藏sealbitmap,展示EditText标准编辑框
            mSealTextClickListener.onTextRectInSideClick();

        } else {

            MuLog.logd("onTouchEvent onTextRectOutSideClick" );
            //如果在区域外，则进入此逻辑：隐藏sealbitmap,展示EditText标准编辑框
            mSealTextClickListener.onTextRectOutSideClick();

            //隐藏seal区域bitmap绘制
//            bitmapSeal = null;
        }
    }


    public void setTextClickListener(SealTextClickListener listener) {
        mSealTextClickListener = listener;
    }


    /**
     * set 盖章 bitmap
     */
    public void setSealBitmap(Bitmap bitmap) {
        mHolder.bitmapSeal = bitmap;
        invalidate();
    }
}
