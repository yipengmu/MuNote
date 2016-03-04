package com.laomu.note.module.share;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.laomu.note.R;
import com.laomu.note.common.MuLog;
import com.laomu.note.common.screenshot.ScreenshotManager;
import com.laomu.note.common.screenshot.view.ScreenshotView;
import com.laomu.note.common.screenshot.view.SealTextClickListener;
import com.laomu.note.ui.base.NoteBaseActivity;

/**
 * Created by ${yipengmu} on 16/3/3.
 */
public class ScreenShotActivity extends NoteBaseActivity implements SealTextClickListener {

    private ScreenshotView mScreenshotView;
    private Bitmap mScreenshotBgBitmap;
    private Bitmap mEditTextUiBitmap;
    private Button mBtnAddText;
    private EditText mEtTagText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.screenshot_layout);

        initToolbar();

        initData();

        initView();
    }

    private void initView() {
        mScreenshotView = (ScreenshotView) findViewById(R.id.iv_screenshot);
        mBtnAddText = (Button) findViewById(R.id.btn_add_text);
        mEtTagText = (EditText) findViewById(R.id.et_tag_text);


        initScreenshotView();
        //处理文本框文案数据
        initSSEditText();

        mBtnAddText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEtTagText.requestFocus();
            }
        });

    }

    private void initScreenshotView() {

        mScreenshotView.setTextClickListener(this);
        //绘制背景图片
        if (mScreenshotBgBitmap != null) {
            mScreenshotView.setBackground(new BitmapDrawable(mScreenshotBgBitmap));
            mScreenshotView.invalidate();
        }

        mScreenshotView.post(new Runnable() {
            @Override
            public void run() {
                mScreenshotView.setSealTextLayout(mEtTagText.getX(),mEtTagText.getTop());
            }
        });
    }

    private void initSSEditText() {


        mEtTagText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus == false) {
                    v.setVisibility(View.INVISIBLE);
                }

//                ScreenshotManager.setEditTextUIBitmap(ScreenshotManager.getBitmapFromView(v));
                mScreenshotView.setSealTextBitmap(ScreenshotManager.getBitmapFromView(v));
                mScreenshotView.invalidate();
            }
        });

        //获取drawingcache后隐藏输入框，方便后续screenshotView 做touch动作
        mEtTagText.post(new Runnable() {
            @Override
            public void run() {
                mEtTagText.setDrawingCacheEnabled(true);
                mEditTextUiBitmap = ScreenshotManager.getBitmapFromView(mEtTagText);

                if (mEditTextUiBitmap != null) {
                    ScreenshotManager.setEditTextUIBitmap(mEditTextUiBitmap);

                    ScreenshotManager.saveScreenShotToSDCard(mEditTextUiBitmap);
                    mScreenshotView.invalidate();
                }

                mEtTagText.setVisibility(View.GONE);
            }
        });

    }


    private void initData() {
        try {
            mScreenshotBgBitmap = ScreenshotManager.getscreenShotBgBitmap();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_toolbar);

        // Title
        toolbar.setTitle("创意打标");
        setSupportActionBar(toolbar);
//		返回键是否显示出来
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onTextRectOutSideClick() {

        MuLog.logd("onTextRectOutSideClick");
        mEtTagText.setVisibility(View.VISIBLE);
        mEtTagText.requestFocus();
    }


    @Override
    public void onTextRectInSideClick() {
        MuLog.logd("onTextRectInSideClick");
        mEtTagText.setVisibility(View.GONE);
        mEtTagText.clearFocus();
    }
}
