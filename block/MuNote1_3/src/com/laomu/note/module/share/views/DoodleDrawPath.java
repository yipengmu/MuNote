package com.laomu.note.module.share.views;

import android.graphics.Paint;
import android.graphics.Path;

/**
 * Created by ${yipengmu} on 16/3/4.
 */
public class DoodleDrawPath {

    public Path path;

    public Paint paint;// 画笔

    public DoodleDrawPath() {
        path = new Path();
        paint = new Paint();
    }
}
