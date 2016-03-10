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

import com.laomu.note.R;
import com.laomu.note.common.MuLog;
import com.laomu.note.module.share.ScreenShotActivity;
import com.laomu.note.module.share.ScreenshotManager;
import com.laomu.note.module.share.listener.ColorSelectorListener;
import com.laomu.note.module.share.listener.RegionClickListener;
import com.laomu.note.module.share.type.ScreenShotModeEnum;
import com.laomu.note.module.share.views.DoodleTouchView;
import com.laomu.note.module.share.views.EditTextWatcher;
import com.laomu.note.module.share.views.LinearColorSelectorView;
import com.laomu.note.module.share.views.SealEditText;
import com.laomu.note.module.share.views.StickerView;
import com.laomu.note.ui.NoteApplication;


public class ImageEditFragment extends Fragment implements RegionClickListener,ColorSelectorListener {

    private ScreenShotActivity mActivity;
    private Button mBtnAddText;
    private DoodleTouchView doodleTouchView;
    private SealEditText mEtTagText;
    private ViewGroup rlScreenshotTexteditContainer;
    private Bitmap mEditTextUiBitmap;
    private StickerView mStickerView;
    private Button btnHistory1;
    private Button btnHistory2;
    private ScreenShotModeEnum mMode;
    private LinearColorSelectorView linearColorSelectorView;

    public ImageEditFragment() {
        // Required empty public constructor
    }


    public static ImageEditFragment newInstance(ScreenShotActivity screenShotActivity) {
        ImageEditFragment fragment = new ImageEditFragment();
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

        mEtTagText = (SealEditText) view.findViewById(R.id.et_tag_text);
        rlScreenshotTexteditContainer = (ViewGroup) view.findViewById(R.id.rl_screenshot_textedit_container);
        mStickerView = (StickerView) view.findViewById(R.id.img_test);
        doodleTouchView = (DoodleTouchView) view.findViewById(R.id.doodle_touch_view);
        linearColorSelectorView = mActivity.linearColorSelectorView;
        linearColorSelectorView.setColorSelectorListener(this);

        btnHistory1 = mActivity.btnHistory1;
        btnHistory2 = mActivity.btnHistory2;

        mBtnAddText = mActivity.mBtnAddText;
        mStickerView.setRegionClickListener(this);

        updateEditMode(ScreenShotModeEnum.MODE_TEXT);

        mStickerView.post(new Runnable() {
            @Override
            public void run() {
                mStickerView.addBitImage(BitmapFactory.decodeResource(NoteApplication.appContext.getResources(), R.drawable.af_seal_text_bg_default));
            }
        });


        rlScreenshotTexteditContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //退出输入框输入态
                if(mEtTagText.isOutofMaxCharNum()){
                    mEtTagText.checkMaxNumber();
                }else {
                    mEtTagText.setVisibility(View.GONE);
                    mStickerView.setVisibility(View.VISIBLE);
                    handleEditTextDrawingCacheBitmap(mEtTagText);
                }
            }
        });



        mBtnAddText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rlScreenshotTexteditContainer.setClickable(true);
                mEtTagText.setVisibility(View.VISIBLE);
                mStickerView.setVisibility(View.GONE);
                mEtTagText.requestFocus();
                mStickerView.invalidate();
            }
        });

        btnHistory1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEtTagText.setVisibility(View.GONE);
                mStickerView.clear();
                mStickerView.addBitImage(ScreenshotManager.getBitmapFromView(btnHistory1));
                mStickerView.invalidate();
            }
        });

        btnHistory2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEtTagText.setVisibility(View.GONE);
                mStickerView.clear();
                mStickerView.addBitImage(ScreenshotManager.getBitmapFromView(btnHistory2));
                mStickerView.invalidate();
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
            linearColorSelectorView.setVisibility(View.VISIBLE);
        } else if (mMode == ScreenShotModeEnum.MODE_TEXT) {
            doodleTouchView.setVisibility(View.GONE);
            linearColorSelectorView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onColorSelectorChange(int color) {
        doodleTouchView.setDoodlePaintColor(color);
    }

    public void cleanDoodleView() {
        doodleTouchView.clearAllDoodles();
    }
}
