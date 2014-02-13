/**
 * 
 */
package com.laomu.note.ui;

import java.util.ArrayList;
import java.util.Date;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.laomu.note.MainActivity;
import com.laomu.note.R;
import com.laomu.note.common.MuLog;
import com.laomu.note.data.NoteBean;
import com.laomu.note.ui.adapter.NoteListViewAdapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ListView;

/**
 * @author luoyuan.myp
 *
 * 2014-2-13
 */
public class NoteMainFragment extends NoteBaseFragment implements OnClickListener  {
	private ListView mNoteListView;
	private NoteListViewAdapter mListViewAdapter;
	private ArrayList<NoteBean> mNoteDBData;
	private View btn_text_make;
	private View btn_camera_make;
	private View btn_speak_make;
	private View mView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return mView = inflater.inflate(R.layout.activity_main, container, false);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
		initData();
		initView(savedInstanceState);
	}
	
	private void initData() {
		mNoteDBData = getNoteDBDatas();
	}

	private ArrayList<NoteBean> getNoteDBDatas() {
		mNoteDBData = new ArrayList<NoteBean>();
		for (int i = 0; i < 70; i++) {
			mNoteDBData
					.add(new NoteBean("欢迎使用", "默认使用数据", String.valueOf(new Date().toGMTString())));
		}
		return mNoteDBData;
	}

	private void initView(Bundle savedInstanceState) {
		btn_text_make = getActivity().findViewById(R.id.btn_text_make);
		btn_camera_make = getActivity().findViewById(R.id.btn_camera_make);
		btn_speak_make = getActivity().findViewById(R.id.btn_speak_make);
		
		btn_text_make.setOnClickListener(this);
		btn_camera_make.setOnClickListener(this);
		btn_speak_make.setOnClickListener(this);
		initNoteListView();
	}

	private void initNoteListView() {
		mNoteListView = (ListView) getActivity().findViewById(R.id.lv_note_main);
		mListViewAdapter = new NoteListViewAdapter();
		mListViewAdapter.setDatasource(getActivity(), mNoteDBData);
		mNoteListView.setAdapter(mListViewAdapter);
	}

	

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.btn_camera_make:
			makeNoteByCamera();
			break;
		case R.id.btn_text_make:
			makeNoteByText();
			break;
		case R.id.btn_speak_make:
			makeNoteBySpeak();
			break;

		default:
			break;
		}
	}

	private void makeNoteBySpeak() {
		MuLog.logi("makeNoteBySpeak start");
	}

	private void makeNoteByCamera() {
		MuLog.logi("makeNoteByCamera start");
	}

	private void makeNoteByText() {
		MuLog.logi("makeNoteByText start");
		
		openPage("text_create", new TextCreateNoteFragment());
	}

}
