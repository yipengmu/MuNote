package com.laomu.note.module.share.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.laomu.note.R;
import com.laomu.note.common.MuLog;
import com.laomu.note.module.share.ScreenShotActivity;
import com.laomu.note.module.share.ScreenshotManager;
import com.laomu.note.module.share.listener.ColorSelectorListener;
import com.laomu.note.module.share.listener.RegionClickListener;
import com.laomu.note.module.share.type.ScreenShotModeEnum;
import com.laomu.note.module.share.views.DoodleTouchView;
import com.laomu.note.module.share.views.LinearColorSelectorView;
import com.laomu.note.module.share.views.StickerView;
import com.laomu.note.ui.NoteApplication;


public class TextModeFragment extends Fragment implements RegionClickListener,ColorSelectorListener {

    private ScreenShotActivity mActivity;
    private Button mBtnAddText;
    private DoodleTouchView doodleTouchView;
    private EditText mEtTagText;
    private ViewGroup rl_screenshot_textedit_container;
    private Bitmap mEditTextUiBitmap;
    private StickerView mStickerView;
    private Button btnHistory1;
    private Button btnHistory2;
    private ScreenShotModeEnum mMode;
    private LinearColorSelectorView linearColorSelectorView;

    public TextModeFragment() {
        // Required empty public constructor
    }


    public static TextModeFragment newInstance(ScreenShotActivity screenShotActivity) {
        TextModeFragment fragment = new TextModeFragment();
        fragment.setActivity(screenShotActivity);
        return fragment;
    }

    public void setActivity(ScreenShotActivity activity) {
        mActivity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_text_mode, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View view) {

        mEtTagText = (EditText) view.findViewById(R.id.et_tag_text);
        rl_screenshot_textedit_container = (ViewGroup) view.findViewById(R.id.rl_screenshot_textedit_container);
        mStickerView = (StickerView) view.findViewById(R.id.img_test);
        doodleTouchView = (DoodleTouchView) view.findViewById(R.id.doodle_touch_view);

        linearColorSelectorView = (LinearColorSelectorView) view.findViewById(R.id.color_selector_view);
        linearColorSelectorView.setColorSelectorListener(this);

        btnHistory1 = mActivity.btnHistory1;
        btnHistory2 = mActivity.btnHistory2;

        mBtnAddText = mActivity.mBtnAddText;
        mStickerView.setRegionClickListener(this);

        updateEditMode(ScreenShotModeEnum.MODE_TEXT);

        mStickerView.post(new Runnable() {
            @Override
            public void run() {
                mStickerView.addBitImage(BitmapFactory.decodeResource(NoteApplication.appContext.getResources(), R.drawable.ic_launcher_fang));
            }
        });


        mBtnAddText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl_screenshot_textedit_container.setClickable(true);
                mEtTagText.setVisibility(View.VISIBLE);
                mStickerView.setVisibility(View.GONE);
                mEtTagText.requestFocus();
            }
        });

        rl_screenshot_textedit_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //退出输入框输入态
                mEtTagText.setVisibility(View.GONE);
                mStickerView.setVisibility(View.VISIBLE);
                handleEditTextDrawingCacheBitmap(mEtTagText);
            }
        });


        btnHistory1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStickerView.clear();
                mStickerView.addBitImage(ScreenshotManager.getBitmapFromView(btnHistory1));
            }
        });

        btnHistory2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStickerView.clear();
                mStickerView.addBitImage(ScreenshotManager.getBitmapFromView(btnHistory2));
            }
        });

    }

    private void handleEditTextDrawingCacheBitmap(EditText editText) {
        mEditTextUiBitmap = ScreenshotManager.getBitmapFromView(editText);

        if (mEditTextUiBitmap != null) {
            mStickerView.clear();
            mStickerView.addBitImage(mEditTextUiBitmap);
            ScreenshotManager.setEditTextUIBitmap(mEditTextUiBitmap);
        }

    }

    @Override
    public void onTextRectOutSideClick() {
        MuLog.logd("onTextRectOutSideClick");
        mEtTagText.setVisibility(View.GONE);
    }


    @Override
    public void onTextRectInSideClick() {
        MuLog.logd("onTextRectInSideClick");

        mStickerView.setVisibility(View.GONE);
        mEtTagText.setVisibility(View.VISIBLE);
        mEtTagText.requestFocus();
    }


    public void updateEditMode(ScreenShotModeEnum mode) {
        mMode = mode;

        if (mMode == ScreenShotModeEnum.MODE_DOODLE) {
            doodleTouchView.setVisibility(View.VISIBLE);
        } else if (mMode == ScreenShotModeEnum.MODE_TEXT) {
            doodleTouchView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onColorSelectorChange(int color) {
        doodleTouchView.setDoodlePaintColor(color);
    }
}
