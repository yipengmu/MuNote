package com.laomu.note.ui.act.help;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.laomu.note.R;
import com.laomu.note.ui.base.NoteBaseActivity;
import com.laomu.note.ui.webview.WebviewActivity;

/**
 * Created by ${yipengmu} on 15/8/16.
 */
public class HelpAct extends NoteBaseActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help_act);

        initToolbar("帮助与反馈");

        findViewById(R.id.tv_app_history).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAppHistoryVersion();
            }
        });
    }


    private void openAppHistoryVersion() {
        Intent intent = new Intent(this,WebviewActivity.class);

        intent.putExtra("url", "file:///android_asset/history.html");
        intent.putExtra("title", "历史版本");

        startActivity(intent);
    }
}
