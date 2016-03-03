package com.laomu.note.module.share;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.laomu.note.R;
import com.laomu.note.common.screenshot.ScreenshotManager;
import com.laomu.note.common.screenshot.view.ScreenshotView;
import com.laomu.note.ui.base.NoteBaseActivity;

/**
 * Created by ${yipengmu} on 16/3/3.
 */
public class ScreenShotActivity extends NoteBaseActivity {

    private ScreenshotView ivBitmap;
    private Bitmap mBitmap;
    private Button btnAddText;
    private EditText etTagText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.screenshot_layout);

        initToolbar();

        initData();

        initView();
    }

    private void initView() {
        ivBitmap = (ScreenshotView) findViewById(R.id.iv_screenshot);
        btnAddText = (Button) findViewById(R.id.btn_add_text);
        etTagText = (EditText) findViewById(R.id.et_tag_text);


        if(mBitmap != null){
            ivBitmap.setImageBackgroud(mBitmap);
        }

        btnAddText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etTagText.requestFocus();
            }
        });



    }


    private void initData() {
        try {
            mBitmap = ScreenshotManager.getScreenShotBitmap();
        }catch (Exception e){
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
}
