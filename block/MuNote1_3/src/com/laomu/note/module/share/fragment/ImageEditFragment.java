package com.laomu.note.module.share.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;

import com.laomu.note.R;
import com.laomu.note.common.MuLog;
import com.laomu.note.module.share.ScreenShotActivity;
import com.laomu.note.module.share.ScreenshotManager;
import com.laomu.note.module.share.listener.ColorSelectorListener;
import com.laomu.note.module.share.listener.LinearPaletteTouchListener;
import com.laomu.note.module.share.listener.RegionClickListener;
import com.laomu.note.module.share.type.ScreenShotModeEnum;
import com.laomu.note.module.share.views.DoodleTouchView;
import com.laomu.note.module.share.views.LinearPaletteSelectorView;
import com.laomu.note.module.share.views.SealEditText;
import com.laomu.note.module.share.views.StickerView;
import java.util.ArrayList;


public class ImageEditFragment extends Fragment implements RegionClickListener,ColorSelectorListener,LinearPaletteTouchListener {

    private ScreenShotActivity mActivity;
    private Button mBtnAddText;
    private DoodleTouchView doodleTouchView;
    private SealEditText mEtTagText;
    private ViewGroup rlScreenshotTexteditContainer;
    private Bitmap mEditTextUiBitmap;
    private StickerView mStickerView;
    private ScreenShotModeEnum screenShotModeEnum;
    private LinearPaletteSelectorView linearPaletteSelectorView;
    public ArrayList<Button> sealHistoryItems;
    private String editTextCurrentString = "轻触编辑文案";

    public ImageEditFragment() {
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
        linearPaletteSelectorView = mActivity.linearPaletteSelectorView;
        linearPaletteSelectorView.setColorSelectorListener(this);
        doodleTouchView.setLinearPaletteTouchListener(this);
        sealHistoryItems = mActivity.sealHistoryItems;
        mBtnAddText = mActivity.mBtnAddText;
        mStickerView.setRegionClickListener(this);

        updateEditMode(ScreenShotModeEnum.MODE_TEXT);

        rlScreenshotTexteditContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //退出输入框输入态
                if (mEtTagText.isOutofMaxCharNum()) {
                    mEtTagText.checkMaxNumber();
                } else if (mEtTagText.isShown()) {
                    mEtTagText.setVisibility(View.GONE);
                    mStickerView.setVisibility(View.VISIBLE);

                    mEditTextUiBitmap = ScreenshotManager.getBitmapFromView(mEtTagText);

                    if (mEditTextUiBitmap != null) {
                        //可能导致选装框不消失
                        mStickerView.updateBitImage(mEditTextUiBitmap, 1);
                    }

                }
            }
        });


        mEtTagText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean bfocus) {
                if (!bfocus) {

                    mEditTextUiBitmap = ScreenshotManager.getBitmapFromView(mEtTagText);
                    mStickerView.clear();
                    mStickerView.addBitImage(mEditTextUiBitmap);
                }

            }
        });


        mBtnAddText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEtTagText.clearFocus();
                rlScreenshotTexteditContainer.setClickable(true);
                mEtTagText.setVisibility(View.VISIBLE);
                mStickerView.setVisibility(View.GONE);
                mEtTagText.requestFocus();
                mStickerView.invalidate();
            }
        });

        initSealHistoryItems();


    }

    private void initSealHistoryItems() {
        for (int i = 0; sealHistoryItems != null && i < sealHistoryItems.size(); i++) {
            final int finalI = i;
            sealHistoryItems.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    doSealItemsClick(sealHistoryItems.get(finalI).getText().toString());
                }
            });
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doSealItemsClick("");
            }
        },50);
    }

    private void doSealItemsClick(String sealText) {
        mEtTagText.clearFocus();
        editTextCurrentString = sealText ;
        mEtTagText.setText(editTextCurrentString);
        mStickerView.updateBitImage(ScreenshotManager.getBitmapFromView(mEtTagText));
        mEtTagText.setVisibility(View.GONE);
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
        screenShotModeEnum = mode;
 
        if (screenShotModeEnum == ScreenShotModeEnum.MODE_DOODLE) {
            doodleTouchView.setVisibility(View.VISIBLE);
            linearPaletteSelectorView.setVisibility(View.VISIBLE);
        } else if (screenShotModeEnum == ScreenShotModeEnum.MODE_TEXT) {
            doodleTouchView.setVisibility(View.GONE);
            linearPaletteSelectorView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onColorSelectorChange(int color) {
        doodleTouchView.setDoodlePaintColor(color);
    }

    public void cleanDoodleView() {
        doodleTouchView.clearAllDoodles();
    }


    @Override
    public void onPaletteTouchDown() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);
        alphaAnimation.setDuration(300);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                linearPaletteSelectorView.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        linearPaletteSelectorView.startAnimation(alphaAnimation);
    }

    @Override
    public void onPaletteTouchUp() {

        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        alphaAnimation.setDuration(500);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                linearPaletteSelectorView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        linearPaletteSelectorView.startAnimation(alphaAnimation);
    }

    public String getEditTextCurrentString() {
        editTextCurrentString = mEtTagText.getText().toString();
        return editTextCurrentString;
    }
}
