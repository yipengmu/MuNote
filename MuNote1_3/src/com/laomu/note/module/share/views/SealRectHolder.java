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

    // 文案编辑框起始X，起始Y
    private float startX;
    private float startY;
    public Bitmap bitmapSeal;
    public Bitmap bitmapDel;
    public Bitmap bitmapRotate;

    public SealRectHolder() { // 导入工具按钮位图
        if (bitmapDel == null) {
            bitmapDel = BitmapFactory.decodeResource(NoteApplication.appContext.getResources(),
                    R.drawable.sticker_delete);
        }// end if
        if (bitmapRotate == null) {
            bitmapRotate = BitmapFactory.decodeResource(NoteApplication.appContext.getResources(),
                    R.drawable.sticker_rotate);
        }


        MuLog.logd("startX= " + startX + "  startY= " + startY);
    }


    public float getRatateIconX() {
        MuLog.logd("RatateIcon startX=" + startX + " bitmapSeal.getWidth()=" + bitmapSeal.getWidth());

        return startX + bitmapSeal.getWidth() - bitmapRotate.getWidth();
    }

    public float getRatateIconY() {
        MuLog.logd("RatateIcon startY=" + startY + " bitmapSeal.getHeight()=" + bitmapSeal.getHeight());

        return startY + bitmapSeal.getHeight()  - bitmapRotate.getHeight();

    }

    public float getDelateIconX() {

        return startX;
    }

    public float getDelateIconY() {
        return startY;
    }


    public float getSealWidth() {

        return bitmapSeal.getWidth();
    }

    public float getSealHeight() {
        return bitmapSeal.getHeight();
    }


}
