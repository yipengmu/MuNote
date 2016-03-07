package com.laomu.note.module.share.views;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.laomu.note.R;
import com.laomu.note.common.MuLog;
import com.laomu.note.ui.NoteApplication;

/**
 * Created by ${yipengmu} on 16/3/4.
 */
public class SealRectHolder {

    // 文案编辑框起始 偏移量 X、Y
    private float mDeltaX = 0;
    private float mDeltaY = 0;

    public Bitmap bitmapSeal;
    public Bitmap bitmapDel;
    public Bitmap bitmapRotate;

    public float mLastEventX = 0;
    public float mLastEventY = 0;


    public void setDeltaY(float mDeltaY) {
        this.mDeltaY = mDeltaY;
    }

    public void setDeltaX(float mDeltaX) {
        this.mDeltaX = mDeltaX;
    }

    public float getmDeltaY() {
        return mDeltaY;
    }

    public float getmDeltaX() {
        return mDeltaX;
    }

    public float getLastEventY() {
        return mLastEventY;
    }

    public void setLastEventY(float mLastEventY) {
        this.mLastEventY = mLastEventY;
    }

    public float getLastEventX() {
        return mLastEventX;
    }

    public void setLastEventX(float mLastEventX) {
        this.mLastEventX = mLastEventX;
    }

    public int getmContainerWidth() {
        return mContainerWidth;
    }

    public int getmContainerHeight() {
        return mContainerHeight;
    }

    private int mContainerWidth;
    private int mContainerHeight;

    public SealRectHolder() { // 导入工具按钮位图
        if (bitmapDel == null) {
            bitmapDel = BitmapFactory.decodeResource(NoteApplication.appContext.getResources(),
                    R.drawable.sticker_delete);
        }// end if
        if (bitmapRotate == null) {
            bitmapRotate = BitmapFactory.decodeResource(NoteApplication.appContext.getResources(),
                    R.drawable.sticker_rotate);
        }


    }


    public float getRatateIconX() {
        MuLog.logd("RatateIcon getSealX()=" + getSealX() + "  getSealWidth()=" + getSealWidth());

        return getSealX() + getSealWidth() - bitmapRotate.getWidth();
    }

    public float getRatateIconY() {
        MuLog.logd("RatateIcon getSealY()=" + getSealY() + "  getSealHeight()=" + getSealHeight());

        return getSealY() + getSealHeight() - bitmapRotate.getHeight();

    }

    public float getDelateIconX() {

        return mContainerWidth != 0 ? getSealX() : 0;
    }

    public float getDelateIconY() {
        return mContainerHeight != 0 ? getSealY() : 0;
    }


    public float getSealWidth() {

        return bitmapSeal.getWidth();
    }

    public float getSealHeight() {
        return bitmapSeal.getHeight();
    }


    public float getSealX() {
        return (mContainerWidth - getSealWidth()) / 2 + mDeltaX;
    }


    public float getSealY() {
        return (mContainerHeight - getSealHeight()) / 2 + mDeltaY;
    }

    public void setContainerLayout(int width, int height) {
        mContainerHeight = height;
        mContainerWidth = width;

        MuLog.logd("setContainerLayout width=" + width + " height=" + height);
    }
}
