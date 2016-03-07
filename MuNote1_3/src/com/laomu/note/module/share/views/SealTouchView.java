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
    private float screenWidth = 500, screenHeight = 500;

    private SealRectHolder mSealrectHolder;
    private SealTextClickListener mSealTextClickListener;
    private Canvas mCanvas;

    public RectF detectRotateRect;
    public RectF detectDeleteRect;


    public SealTouchView(Context context) {
        super(context);

        // view初始化
        init(screenWidth, screenHeight);
    }

    public SealTouchView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // view初始化
        init(screenWidth, screenHeight);
    }

    public SealTouchView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        // view初始化
        init(screenWidth, screenHeight);
    }

    public void init(float w, float h) {
        mSealrectHolder = new SealRectHolder();
        mSealrectHolder.bitmapSeal = Bitmap.createBitmap((int) screenWidth, (int) screenHeight,
                Bitmap.Config.ARGB_8888);

        // 保存一次一次绘制出来的图形
        mCanvas = new Canvas(mSealrectHolder.bitmapSeal);

        mSealPaint = new Paint();
        mSealPaint.setColor(Color.RED);
        mSealPaint.setStyle(Paint.Style.FILL);
        mSealPaint.setTextSize(40);
        mSealPaint.setAntiAlias(true);


        mSealrectHolder.bitmapSeal = ScreenshotManager.getEditTextUIBitmap();

        mSealrectHolder = new SealRectHolder();


    }


    @Override
    protected void onDraw(Canvas canvas) {
        MuLog.logd("onDraw bitmapSeal = " + mSealrectHolder.bitmapSeal.getWidth() + " " + mSealrectHolder.bitmapSeal.getHeight());
        MuLog.logd("onDraw bitmapSeal RatateIcon= " + mSealrectHolder.getRatateIconX() + " " + mSealrectHolder.getRatateIconY());

        if (mSealrectHolder.bitmapSeal != null) {
            canvas.drawBitmap(mSealrectHolder.bitmapSeal, 0, 0, null);

            //绘制 删除x 和 旋转箭头 按钮
            canvas.drawBitmap(mSealrectHolder.bitmapDel, mSealrectHolder.getDelateIconX(), mSealrectHolder.getDelateIconY(), null);

            canvas.drawBitmap(mSealrectHolder.bitmapRotate, mSealrectHolder.getRatateIconX(), mSealrectHolder.getRatateIconY(), null);
        }
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
//                touch_move(x, y);
                break;
            case MotionEvent.ACTION_UP:
//                touch_up();
                break;
        }
        MuLog.logd("onTouchEvent event.getX()=" + event.getX() + " event.getY()=" + event.getY());

        invalidate();
        return true;
    }

    private void touchDown(float x, float y) {

    }

    private void checkSealTextClick(float x, float y) {
        if (mSealTextClickListener == null) {
            return;
        }

        //判断当前按下触发的是否是落在编辑框区域中
        if (x > 0 && x < mSealrectHolder.getSealWidth() && (y > 0 && y < mSealrectHolder.getSealHeight())) {

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
        mSealrectHolder.bitmapSeal = bitmap;
        invalidate();
    }
}
