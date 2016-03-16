package com.laomu.note.module.share.views;

import android.app.Application;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by ${yipengmu} on 16/3/10.
 */
public class EditTextWatcher implements TextWatcher {
    private CharSequence innerContent;
    private EditText editTextCaller;
    private final int MAX_CHAR_NUMBER = 5;
    private Context context;
    private boolean bOutofMaxChars = false;
    private long lastToastTimeSnap = 0l;
    //3秒内只提示一次
    private long TIME_GAP = 1000 * 3;

    public EditTextWatcher(EditText editTextCaller, Context context) {
        this.editTextCaller = editTextCaller;
        this.context = context;
    }

    public boolean isOutofMaxChars() {
        return bOutofMaxChars;
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        innerContent = s;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {
        editTextCaller.setVisibility(View.VISIBLE);
    }

    @Override
    public void afterTextChanged(Editable s) {
        checkMaxNumber();

    }

    public void checkMaxNumber() {
        int deltaLenth = editTextCaller.getText().length() - MAX_CHAR_NUMBER;
        if(deltaLenth > 0){
            if( lastToastTimeSnap == 0L){
                Toast.makeText(context, "已超" + deltaLenth + "个字", Toast.LENGTH_SHORT).show();
                lastToastTimeSnap = System.currentTimeMillis();
            }else if(lastToastTimeSnap !=0L &&  System.currentTimeMillis() - lastToastTimeSnap > TIME_GAP){
                Toast.makeText(context, "已超" + deltaLenth + "个字", Toast.LENGTH_SHORT).show();
            }
            bOutofMaxChars = true;
        }else {
            bOutofMaxChars = false;
        }
    }
}
