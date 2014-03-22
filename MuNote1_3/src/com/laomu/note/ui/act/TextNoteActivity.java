/**
 * 
 */
package com.laomu.note.ui.act;

import com.laomu.note.R;
import com.laomu.note.data.DBManager;
import com.laomu.note.data.NoteBean;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.widget.EditText;

/**
 * @author luoyuan.myp
 *
 * 2014-2-17
 */
public class TextNoteActivity extends Activity {

	private NoteBean mNoteBean;
	private int MODE_CREATE = 1;
	private int MODE_EDIT = 2;
	private int mMode = 0;
	private EditText et_text_note;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.text_create_note);
		
		initData();
		initView();
	}
	
	private void initView() {
		et_text_note = (EditText)findViewById(R.id.et_text_note);		
		
		if(null != mNoteBean){
			et_text_note.setText("" + mNoteBean.note_content);
		}
	}

	private void initData() {
		mNoteBean = getIntent().getParcelableExtra("note_bean");

		initMode();
	}

	private void initMode() {
		if(null == mNoteBean){
			mMode = MODE_CREATE;
		}else{
			mMode = MODE_EDIT;
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
	
		saveNote();
	}

	private void saveNote() {
		//save content
		saveNoteContent();
		if(mMode == MODE_CREATE){
			DBManager.instance(getApplicationContext()).add(mNoteBean);
		}else{
			DBManager.instance(getApplicationContext()).updateAge(mNoteBean);
		}
	}

	private void saveNoteContent() {
		String label = et_text_note.getText().toString().trim();
		String labelTitle = label;
		
		if(null == mNoteBean){
			mNoteBean = new NoteBean();
		}
		mNoteBean.note_content = label;
		if(!TextUtils.isEmpty(labelTitle) && labelTitle.length() > 10){
			labelTitle = labelTitle.substring(0, 10);
		}
		mNoteBean.note_title = labelTitle;
		mNoteBean.note_time = "" + SystemClock.currentThreadTimeMillis();
	}
}
