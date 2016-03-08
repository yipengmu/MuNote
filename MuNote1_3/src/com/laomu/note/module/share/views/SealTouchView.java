package com.laomu.note.module.share.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.laomu.note.common.MuLog;
import com.laomu.note.module.share.ScreenshotManager;
import com.laomu.note.module.share.listener.SealTextClickListener;
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

    private Matrix matrix = new Matrix();


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

        initMatrix();
    }

    private void initMatrix() {
//        matrix.postScale(1.2F, 1.2F);
        matrix.postRotate(20);
//        matrix.postTranslate(mHolder.getDeltaX()/mHolder.getSealWidth()+1, mHolder.getDeltaY()/mHolder.getSealHeight() +1);
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

        if (touchModeEnum == TouchModeEnum.MOVE) {
            canvas.drawBitmap(mHolder.bitmapSeal, mHolder.getSealX(), mHolder.getSealY(), null);

            //绘制 删除x 和 旋转箭头 按钮
            canvas.drawBitmap(mHolder.bitmapDel, mHolder.getDelateIconX(), mHolder.getDelateIconY(), null);

            canvas.drawBitmap(mHolder.bitmapRotate, mHolder.getRatateIconX(), mHolder.getRatateIconY(), null);
        } else if (touchModeEnum == TouchModeEnum.ROTATE_OR_SCALE) {

            canvas.drawBitmap(mHolder.bitmapSeal, matrix, null);
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchDown(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                touchMove(x, y);
                break;
            case MotionEvent.ACTION_UP:
                touchUp();
                break;
        }

        MuLog.logd("onTouchEvent event.getX()=" + x + " event.getY()=" + y);

        invalidate();
        return true;
    }

    private void touchDown(float x, float y) {
        if (touchModeEnum == TouchModeEnum.DELETE) {
            return;
        }

        if (x >= mHolder.getDelateIconX() && x <= mHolder.getDelateIconX() + mHolder.bitmapDel.getWidth() &&
                y >= mHolder.getDelateIconY() && y <= mHolder.getDelateIconY() + mHolder.bitmapDel.getHeight()) {
            touchModeEnum = TouchModeEnum.DELETE;
        } else if (x >= mHolder.getRatateIconX() && x <= mHolder.getRatateIconX() + mHolder.bitmapRotate.getWidth() &&
                y > mHolder.getRatateIconY() && y <= mHolder.getRatateIconY() + mHolder.bitmapRotate.getHeight()) {
            touchModeEnum = TouchModeEnum.ROTATE_OR_SCALE;
        } else {
            touchModeEnum = TouchModeEnum.MOVE;
        }

        MuLog.logd("touchDown touchModeEnum= " + touchModeEnum);

        checkSealTextClick(x, y);
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
        }

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

//        if(touchModeEnum == TouchModeEnum.ROTATE_OR_SCALE){
//            if (mHolder.checkScale()) {//图片拖动事件
//                float dx = x - startPoint.x;//x轴移动距离
//                float dy = y - startPoint.y;
//                matrix.set(currentMaritx);//在当前的位置基础上移动
//                matrix.postTranslate(dx, dy);
//
//            } else if(mHolder.checkZoom()) {//图片放大事件
//                float endDis = mHolder.touchDistance(x,y);//结束距离
//                if (endDis > 10f) {
//                    float scale = mHolder.getScale(endDis);
//                    matrix.set(currentMaritx);
//                    matrix.postScale(scale, scale, mHolder.getMiddleSealX(), mHolder.getMiddleSealY());
//                }
//            }
//
//        }
        float endDis = mHolder.touchDistance(x, y);//结束距离
        if (endDis > 5f) {
            matrix.postRotate(mHolder.getScale(endDis), mHolder.getMiddleSealX(), mHolder.getMiddleSealY());
        }
        matrix.postTranslate(mHolder.getDeltaX(), mHolder.getDeltaY());
        MuLog.logd("mDeltaX= " + mHolder.getDeltaX() + "  mDeltaY= " + mHolder.getDeltaY());
    }


    private void touchUp() {
        resetTouchModeEnum();
    }


    public void resetTouchModeEnum() {
        touchModeEnum = TouchModeEnum.MOVE;
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



    private void clearCanvas(Canvas canvas) {
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
    }

    private void clearTouchModeEnum() {
        touchModeEnum = TouchModeEnum.MOVE;
    }

}
