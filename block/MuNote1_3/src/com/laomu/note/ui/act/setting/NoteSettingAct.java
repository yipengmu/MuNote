package com.laomu.note.ui.act.setting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.laomu.note.R;
import com.laomu.note.ui.act.business.AboutUsActivity;
import com.laomu.note.ui.base.NoteBaseActivity;
import com.laomu.note.ui.webview.WebviewActivity;


public class NoteSettingAct extends NoteBaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.setting_act);
//        setTitle();
        initToolbar();
        findViewById(R.id.tv_about_app).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(NoteSettingAct.this,AboutUsActivity.class));
            }
        });
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_toolbar);

        // Title
        toolbar.setTitle("设置");
        // Sub Title
//		toolbar.setSubtitle("长按图标试试~");

        setSupportActionBar(toolbar);

//		toolbar.setNavigationIcon(R.drawable.icon_menu_voice);

//		返回键是否显示出来
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Menu item click 的監聽事件一樣要設定在 setSupportActionBar 才有作用
//        toolbar.setOnMenuItemClickListener(onMenuItemClick);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }



    private void gotoAboutUs() {
        Intent intent = new Intent(this,AboutUsActivity.class);
        startActivity(intent);
    }
}
