package com.laomu.note.module.share.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

import com.laomu.note.ui.NoteApplication;

/**
 * Created by ${yipengmu} on 16/3/10.
 */
public class SealEditText extends EditText {
    private EditTextWatcher editTextWatcher;

    public SealEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }
    public SealEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SealEditText(Context context) {
        super(context);
        init();
    }

    private void init() {
        editTextWatcher = new EditTextWatcher(this, NoteApplication.appContext);
        addTextChangedListener(editTextWatcher);
    }


    public boolean isOutofMaxCharNum() {
        return editTextWatcher.isOutofMaxChars();
    }


    public void checkMaxNumber() {
        editTextWatcher.checkMaxNumber();
    }
}
