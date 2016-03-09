package com.laomu.note.module.share;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.laomu.note.R;
import com.laomu.note.module.share.fragment.DoodleModeFragment;
import com.laomu.note.module.share.fragment.TextModeFragment;
import com.laomu.note.module.share.type.ScreenShotModeEnum;
import com.laomu.note.ui.base.NoteBaseActivity;


/**
 * Created by ${yipengmu} on 16/3/3.
 */
public class ScreenShotActivity extends NoteBaseActivity{

    private TextView mTvModeText, mTvModeDoodle;
    private FragmentManager fragmentManager;
    private FrameLayout flScreenshotFragmentContainer;
    private TextModeFragment mTextModeFragment;
    private DoodleModeFragment mDoodleModeFragment;
    private FragmentTransaction mFragmentTransaction;
    private ImageView img_screen_bg;
    //保存的背景截图文件名
    private String mEditTextTagBitmapFileName = "screenshot_tag.png";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.screenshot_layout);

        initToolbar();

        initView();
    }

    private void initView() {
        img_screen_bg = (ImageView) findViewById(R.id.img_screen_bg);
        img_screen_bg.setImageBitmap(ScreenshotManager.getscreenShotBgBitmap());
        mTvModeText = (TextView) findViewById(R.id.tv_mode_text);
        mTvModeDoodle = (TextView) findViewById(R.id.tv_mode_doodle);

        mTvModeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFragments(ScreenShotModeEnum.MODE_TEXT);
            }
        });

        mTvModeDoodle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFragments(ScreenShotModeEnum.MODE_DOODLE);
            }
        });

        initFragment();


    }

    private void initFragment() {
        mTextModeFragment = new TextModeFragment();
        mDoodleModeFragment = new DoodleModeFragment();
        fragmentManager = getSupportFragmentManager();

        showFragments(ScreenShotModeEnum.MODE_TEXT);
    }

    private void showFragments(ScreenShotModeEnum mode) {
        mFragmentTransaction = fragmentManager.beginTransaction();

        if(mode == ScreenShotModeEnum.MODE_TEXT){
            mFragmentTransaction.replace(R.id.fl_screenshot_fragment_container, mTextModeFragment);
        }else if(mode == ScreenShotModeEnum.MODE_DOODLE){
            mFragmentTransaction.replace(R.id.fl_screenshot_fragment_container, mDoodleModeFragment);
        }
        mFragmentTransaction.commit();
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

}
