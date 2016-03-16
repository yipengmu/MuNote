package com.laomu.note.module.share;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.laomu.note.R;
import com.laomu.note.common.preferences.PreferenceCenter;
import com.laomu.note.module.share.fragment.ImageEditFragment;
import com.laomu.note.module.share.listener.ImageEditLayoutListener;
import com.laomu.note.module.share.listener.LinearPaletteTouchListener;
import com.laomu.note.module.share.type.ScreenShotModeEnum;
import com.laomu.note.module.share.views.LinearPaletteSelectorView;
import com.laomu.note.ui.base.NoteBaseActivity;

import org.json.JSONArray;

import java.util.ArrayList;


/**
 * Created by ${yipengmu} on 16/3/3.
 */
public class ScreenShotActivity extends NoteBaseActivity implements ImageEditLayoutListener {

    private TextView mTvModeText, mTvModeDoodle;
    private FragmentManager fragmentManager;
    private ImageEditFragment mImageEditFragment;
    private FragmentTransaction mFragmentTransaction;
    private ImageView imgScreenBg;
    public Button mBtnAddText;
    public Button btnCleanDoodle;
    private LinearLayout sealHistoryContainer;
    public ArrayList<Button> sealHistoryItems = new ArrayList<>();
    public LinearPaletteSelectorView linearPaletteSelectorView;
    private FrameLayout screenshotFrameLayoutContainer;


    //保存的背景截图文件名
    public LinearLayout llDoodleoolbar;
    public LinearLayout llSealtextToolbar;
    private FrameLayout mFrameLayoutContainer;
    private View doodleFirsttimeMasker;
    private ScreenShotModeEnum ACTION_MODE = ScreenShotModeEnum.MODE_TEXT;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.screenshot_layout);

        initToolbar();

        initView();
    }

    private void initView() {
        imgScreenBg = (ImageView) findViewById(R.id.img_screen_bg);
        Bitmap bg = ScreenshotManager.getScreenshotBgBitmap();

        imgScreenBg.setImageBitmap(bg);
        mFrameLayoutContainer = (FrameLayout)findViewById(R.id.fl_screenshot_fragment_container);
        updateEditImageFrameLayout(bg);

        mFrameLayoutContainer = (FrameLayout) findViewById(R.id.fl_screenshot_fragment_container);
        mTvModeText = (TextView) findViewById(R.id.tv_mode_text);
        mTvModeDoodle = (TextView) findViewById(R.id.tv_mode_doodle);
        mBtnAddText = (Button) findViewById(R.id.btn_add_text);
        btnCleanDoodle = (Button) findViewById(R.id.btn_clean_doodle);
        sealHistoryContainer = (LinearLayout) findViewById(R.id.ll_seal_history_container);
        llDoodleoolbar = (LinearLayout) findViewById(R.id.ll_doodle_toolbar);
        linearPaletteSelectorView = (LinearPaletteSelectorView) findViewById(R.id.color_selector_view);
        llSealtextToolbar = (LinearLayout) findViewById(R.id.ll_sealtext_toolbar);
        doodleFirsttimeMasker = findViewById(R.id.ll_doodle_firsttime_masker);
        screenshotFrameLayoutContainer = (FrameLayout) findViewById(R.id.fl_screenshot_fragment_container);
        sealHistoryContainer = (LinearLayout) findViewById(R.id.ll_seal_history_container);

        mTvModeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateFragmentMode(ScreenShotModeEnum.MODE_TEXT);
            }
        });

        mTvModeDoodle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateFragmentMode(ScreenShotModeEnum.MODE_DOODLE);
            }
        });

        btnCleanDoodle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mImageEditFragment.cleanDoodleView();
            }
        });


        initFragment();
        initSealHistoryContent();
        checkDoodleFirstTimeMasker();

    }

    private void initSealHistoryContent() {
        JSONArray jsonArray = PreferenceCenter.getPreferences().getSealContentHistory();
        if(jsonArray != null && jsonArray.length() > 0) {
            for (int i = 0; i < jsonArray.length(); i++) {
                Button button = new Button(getApplicationContext());
                button.setText((String) jsonArray.opt(i));
                sealHistoryItems.add(button);
                sealHistoryContainer.addView(button);
            }
        }
    }

    private void updateEditImageFrameLayout(final Bitmap bg) {
        mFrameLayoutContainer.post(new Runnable() {
            @Override
            public void run() {
                mFrameLayoutContainer.getLayoutParams().height = imgScreenBg.getMeasuredHeight();
                mFrameLayoutContainer.getLayoutParams().width = ScreenshotManager.getImageWidthFromStruction(bg, mFrameLayoutContainer.getLayoutParams().height);
                mFrameLayoutContainer.setBackground(new BitmapDrawable(getResources(), bg));
                mFrameLayoutContainer.invalidate();
            }
        });
    }

    private void checkDoodleFirstTimeMasker() {
        boolean isDooledHasShowed = PreferenceCenter.getPreferences().getDoodleFirstShowed();
        if (isDooledHasShowed) {
            doodleFirsttimeMasker.setVisibility(View.GONE);
        } else {
            doodleFirsttimeMasker.setVisibility(View.VISIBLE);
            doodleFirsttimeMasker.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    doodleFirsttimeMasker.setVisibility(View.GONE);
                    PreferenceCenter.getPreferences().setDoodleFirstShowed();
                }

            });
        }

    }

    private void updateFragmentMode(ScreenShotModeEnum mode) {
        ACTION_MODE = mode;
        mImageEditFragment.updateEditMode(mode);

        //处理toolbar
        if (mode == ScreenShotModeEnum.MODE_DOODLE) {
            llDoodleoolbar.setVisibility(View.VISIBLE);
            llSealtextToolbar.setVisibility(View.GONE);
            llDoodleoolbar.setVisibility(View.GONE);
            llSealtextToolbar.setVisibility(View.VISIBLE);
        }
    }

    private void initFragment() {
        mImageEditFragment = ImageEditFragment.newInstance(this);

        fragmentManager = getSupportFragmentManager();
        mFragmentTransaction = fragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.fl_screenshot_fragment_container, mImageEditFragment);
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

        toolbar.setOnMenuItemClickListener(onMenuItemClick);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_share, menu);

        return super.onCreateOptionsMenu(menu);
    }


    private Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_share_next_step:
                    shareForNextStep();
                    break;
            }

            return true;
        }
    };

    private void shareForNextStep() {
        Bitmap finalBitmap = ScreenshotManager.getBitmapFromView(screenshotFrameLayoutContainer);
        ScreenshotManager.setScreenshotResultBitmap(finalBitmap);
        //jump to common share activity
        Toast.makeText(getApplication(), "next step..", Toast.LENGTH_SHORT).show();

        PreferenceCenter.getPreferences().addSealContentHistory(mImageEditFragment.getEditTextCurrentString());
        startActivity(new Intent(this, ScreenShotResultActivity.class));
    }

    @Override
    public void onImageLayoutFinished(float width, float height) {

    }

}
