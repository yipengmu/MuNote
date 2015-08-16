package com.laomu.note.ui.act.help;

import android.os.Bundle;

import com.laomu.note.R;
import com.laomu.note.ui.base.NoteBaseActivity;

/**
 * Created by ${yipengmu} on 15/8/16.
 */
public class HelpAct extends NoteBaseActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help_act);

        initToolbar("帮助与反馈");
    }
}
