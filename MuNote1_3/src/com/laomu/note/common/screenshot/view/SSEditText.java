package com.laomu.note.common.screenshot.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

/**
 * Created by ${yipengmu} on 16/3/4.
 *
 * 创意截屏 文本编辑框
 *
 *
 */
public class SSEditText extends EditText {

    private String mSealText = "轻触编辑文案";

    public SSEditText(Context context) {
        super(context);
    }

    public SSEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        initView();
    }

    private void initView() {

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);

        if(focused == false){
            this.setVisibility(View.GONE);
        }
    }
}
