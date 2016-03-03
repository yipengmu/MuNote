package com.laomu.note.common.screenshot;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

/**
 * Created by ${yipengmu} on 16/3/3.
 * <p/>
 * 鎴睆 cavans 鑷畾涔塿iew
 */
public class ScreenshotView extends View {
    //鑳屾櫙灞�    private Bitmap bitmapBackgroud;
    //娑傞甫灞�    private Bitmap bitmapDoodle;
    //鐩栫珷鏂囨灞�    private Bitmap bitmapSeal;

    //鐩栫珷鏂囨鍐呭
    private String mSealText = "杞昏Е缂栬緫鏂囨";
    //鐩栫珷鐢荤瑪Paint
    private Paint mSealPaint;
    //娑傞甫Paint
    private Paint mDoodlePaint;

    //涓婃touchX
    private float mPreviousX = 0;
    //涓婃touchY
    private float mPreviousY = 0;
    //娑傞甫鎵嬪娍Path
    private Path mDoodlePath;

    //涓籱Cavans
    private Canvas mCavans;
    private Path mlinePath;

    public ScreenshotView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    /**
     * defStyleAttr 鑾峰緱鑷畾涔夋牱寮忓睘鎬�     */
    public ScreenshotView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


        initViews();
    }


    private void initViews() {
        mSealPaint = new Paint();
        mSealPaint.setColor(Color.RED);// 璁剧疆鐢荤瑪棰滆壊
        mSealPaint.setStyle(Paint.Style.FILL);// 璁剧疆鐢荤瑪濉厖
        mSealPaint.setTextSize(20);
        mSealPaint.setAntiAlias(true);

        mDoodlePaint = new Paint();
        mDoodlePaint.setColor(Color.YELLOW);// 璁剧疆鐢荤瑪棰滆壊
        mDoodlePaint.setStyle(Paint.Style.FILL);// 璁剧疆鐢荤瑪濉厖
        mDoodlePaint.setTextSize(8);
        mDoodlePaint.setAntiAlias(true);

        mDoodlePath = new Path();
        mlinePath = new Path();


        mlinePath.lineTo(100,100);
        mlinePath.lineTo(100, 200);
        mlinePath.lineTo(150, 250);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        mCavans = canvas;

        canvas.drawText(mSealText, 150, 150, mSealPaint);

        canvas.drawPath(mDoodlePath, mDoodlePaint);
        canvas.drawPoint(mPreviousX,mPreviousY,mDoodlePaint);

        canvas.drawPath(mlinePath, mSealPaint);
    }

    public void setSealText(String sealText) {
        mSealText = sealText;
        postInvalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 鎸変笅
                touchDown(event);
                break;
            case MotionEvent.ACTION_MOVE:
                // 绉诲姩
                touchMove(event);
                break;
            case MotionEvent.ACTION_UP:
                // 鎶捣
//                mCavans.drawPath(mDoodlePath, mDoodlePaint);
                break;
        }

        invalidate();

        return true;

    }

    private void touchMove(MotionEvent event) {

        float currentX = event.getX();
        float currentY = event.getY();

        final float dx = Math.abs(currentX - mPreviousX);
        final float dy = Math.abs(currentY - mPreviousY);
        //涓ょ偣涔嬮棿鐨勮窛绂诲ぇ浜庣瓑浜�鏃讹紝杩炴帴杩炴帴涓ょ偣褰㈡垚鐩寸嚎

        if (dx >= 3 || dy >= 3)
        {
            //涓ょ偣杩炴垚鐩寸嚎
            mDoodlePath.lineTo(event.getX(), event.getY());
            //绗簩娆℃墽琛屾椂锛岀涓�缁撴潫璋冪敤鐨勫潗鏍囧�灏嗕綔涓虹浜屾璋冪敤鐨勫垵濮嬪潗鏍囧�
            mPreviousX = currentX;
            mPreviousY = currentX;
        }

    }

    //鎵嬫寚鐐逛笅灞忓箷鏃惰皟鐢�   
    private void touchDown(MotionEvent event)
    {
        //閲嶇疆缁樺埗璺嚎锛屽嵆闅愯棌涔嬪墠缁樺埗鐨勮建杩�        mDoodlePath.reset();
        mDoodlePath.lineTo(event.getX(),  event.getY());
    }
}
