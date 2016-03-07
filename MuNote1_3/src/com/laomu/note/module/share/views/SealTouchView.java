package com.laomu.note.module.share.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.laomu.note.common.MuLog;
import com.laomu.note.common.screenshot.ScreenshotManager;
import com.laomu.note.common.screenshot.view.SealTextClickListener;
import com.laomu.note.module.share.type.TouchModeEnum;

/**
 * Created by ${yipengmu} on 16/3/7.
 */
public class SealTouchView extends ImageView {

    private Paint mSealPaint;
    //默认为拖拽平移类型
    private TouchModeEnum touchModeEnum = TouchModeEnum.MOVE;

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
        mHolder.setContainerLayout(getWidth(), getHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        MuLog.logd("onDraw SealTouchView = " + getWidth() + " " + getHeight());
        MuLog.logd("onDraw bitmapSeal = " + mHolder.bitmapSeal.getWidth() + " " + mHolder.bitmapSeal.getHeight());
        MuLog.logd("onDraw bitmapSeal RatateIcon= " + mHolder.getRatateIconX() + " " + mHolder.getRatateIconY());
        clearCanvas(canvas);
        if (touchModeEnum != TouchModeEnum.DELETE) {
            canvas.drawBitmap(mHolder.bitmapSeal, mHolder.getSealX(), mHolder.getSealY(), null);

            //绘制 删除x 和 旋转箭头 按钮
            canvas.drawBitmap(mHolder.bitmapDel, mHolder.getDelateIconX(), mHolder.getDelateIconY(), null);

            canvas.drawBitmap(mHolder.bitmapRotate, mHolder.getRatateIconX(), mHolder.getRatateIconY(), null);
        }

        clearTouchModeEnum();
    }

    private void clearCanvas(Canvas canvas) {
        Paint paint = new Paint();
          paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
          canvas.drawPaint(paint);
          paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
    }

    private void clearTouchModeEnum() {
        touchModeEnum = TouchModeEnum.MOVE;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchDown(x, y);
                checkSealTextClick(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                touchMove(x, y);
                break;
            case MotionEvent.ACTION_UP:
//                touch_up();
                break;
        }
        MuLog.logd("onTouchEvent event.getX()=" + x + " event.getY()=" + y);

        invalidate();
        return true;
    }

    private void touchMove(float x, float y) {
        //计算x y 的差量delta值
        if (Float.floatToIntBits(mHolder.getLastEventX()) == Float.floatToIntBits(0)) {
            mHolder.setLastEventX(x);
        }
        if (x != mHolder.getSealX()) {
            mHolder.setDeltaX(x - mHolder.getLastEventX());
        }

        if (Float.floatToIntBits(mHolder.getLastEventY()) == Float.floatToIntBits(0)) {
            mHolder.setLastEventY(y);
        }
        if (y != mHolder.getSealY()) {

            mHolder.setDeltaY(y - mHolder.getLastEventY());
        }


        MuLog.logd("mDeltaX= " + mHolder.getmDeltaX() + "  mDeltaY= " + mHolder.getmDeltaY());
    }

    private void touchDown(float x, float y) {
        if (x > mHolder.getDelateIconX() && x < mHolder.getDelateIconX() + mHolder.bitmapDel.getWidth() &&
                y > mHolder.getDelateIconY() && y < mHolder.getDelateIconY() + mHolder.bitmapDel.getHeight()) {
            touchModeEnum = TouchModeEnum.DELETE;
        }

        if (x == mHolder.getDelateIconX() && y == mHolder.getDelateIconY()) {
            touchModeEnum = TouchModeEnum.ROTATE_OR_SCALE;
        }
    }

    private void checkSealTextClick(float x, float y) {
        if (mSealTextClickListener == null) {
            return;
        }

        //判断当前按下触发的是否是落在编辑框区域中
        if (x > mHolder.getSealX() && x < mHolder.getSealX() + mHolder.getSealWidth() &&
                (y > mHolder.getSealY() && y < mHolder.getSealY() + mHolder.getSealHeight())) {

            MuLog.logd("onTouchEvent onTextRectInSideClick");
            //如果在区域内，则进入此逻辑：隐藏sealbitmap,展示EditText标准编辑框
            mSealTextClickListener.onTextRectInSideClick();

        } else {

            MuLog.logd("onTouchEvent onTextRectOutSideClick");
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
