package com.laomu.note.module.share;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    public Button mBtnAddText;
    public Button btnCleanDoodle;
    public Button btnHistory1;
    public Button btnHistory2;

    //保存的背景截图文件名
    private String mEditTextTagBitmapFileName = "screenshot_tag.png";
    private LinearLayout llDoodleoolbar;
    private LinearLayout llSealtextToolbar;

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
        mBtnAddText = (Button) findViewById(R.id.btn_add_text);
        btnCleanDoodle = (Button) findViewById(R.id.btn_clean_doodle);
        btnHistory1 = (Button) findViewById(R.id.btn_history1);
        btnHistory2 = (Button) findViewById(R.id.btn_history2);
        llDoodleoolbar = (LinearLayout)findViewById(R.id.ll_doodle_toolbar);
        llSealtextToolbar = (LinearLayout)findViewById(R.id.ll_sealtext_toolbar);

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
        mTextModeFragment = TextModeFragment.newInstance(this);
        mDoodleModeFragment = DoodleModeFragment.newInstance(this);
        fragmentManager = getSupportFragmentManager();

        showFragments(ScreenShotModeEnum.MODE_TEXT);
    }

    private void showFragments(ScreenShotModeEnum mode) {
        mFragmentTransaction = fragmentManager.beginTransaction();

        if(mode == ScreenShotModeEnum.MODE_TEXT){
            mFragmentTransaction.replace(R.id.fl_screenshot_fragment_container, mTextModeFragment);
            llSealtextToolbar.setVisibility(View.VISIBLE);
            llDoodleoolbar.setVisibility(View.GONE);
        }else if(mode == ScreenShotModeEnum.MODE_DOODLE){
            mFragmentTransaction.replace(R.id.fl_screenshot_fragment_container, mDoodleModeFragment);
            llSealtextToolbar.setVisibility(View.GONE);
            llDoodleoolbar.setVisibility(View.VISIBLE);
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
