/**
 * 
 */
package com.laomu.note.ui;

import java.util.ArrayList;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

import com.laomu.note.R;
import com.laomu.note.common.CommonDefine;
import com.laomu.note.common.MuLog;
import com.laomu.note.common.lbs.LocationInfo;
import com.laomu.note.data.DBManager;
import com.laomu.note.data.NoteBean;
import com.laomu.note.ui.act.CameraNoteActivity;
import com.laomu.note.ui.act.TextNoteActivity;
import com.laomu.note.ui.adapter.NoteListViewAdapter;
import com.laomu.note.ui.base.NoteBaseFragment;
import com.laomu.note.ui.share.ShareManeger;
import com.umeng.socialize.common.SocializeConstants;
import com.umeng.socialize.controller.RequestType;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.UMImage;

/**
 * @author luoyuan.myp
 *
 * 2014-2-13
 */
public class NoteMainFragment extends NoteBaseFragment implements OnClickListener ,OnItemLongClickListener,OnItemClickListener {
	private ListView mNoteListView;
	private NoteListViewAdapter mListViewAdapter;
	private ArrayList<NoteBean> mNoteDBData;
	private View btn_text_make;
	private View btn_camera_make;
	private View btn_speak_make;
	private View mView;
	private final int CODE_FOR_TEXT_CREATE = 1;
	private int CODE_FOR_CAMERA_CREATE = 2;
	
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
		mNoteDBData = DBManager.instance(getActivity()).queryAllData();
	}


	private void initView(Bundle savedInstanceState) {
		setTitle(R.drawable.ic_menu_user, R.string.app_name, R.drawable.ic_menu_setting);
		findViews();
		initNoteListView();
	}

	
	private void findViews() {
		btn_text_make = mView.findViewById(R.id.btn_text_make);
		btn_camera_make = mView.findViewById(R.id.btn_camera_make);
		btn_speak_make = mView.findViewById(R.id.btn_speak_make);
		
		btn_text_make.setOnClickListener(this);
		btn_camera_make.setOnClickListener(this);
		btn_speak_make.setOnClickListener(this);
	}

	private void initNoteListView() {
		mNoteListView = (ListView) mView.findViewById(R.id.lv_note_main);
		mListViewAdapter = new NoteListViewAdapter();
		mListViewAdapter.setDatasource(getActivity(), mNoteDBData);
		mNoteListView.setAdapter(mListViewAdapter);

		mNoteListView.setOnItemClickListener(this);
		mNoteListView.setOnItemLongClickListener(this);
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

		Intent intent = new Intent(getActivity(),CameraNoteActivity.class);
		startActivityForResult(intent, CODE_FOR_CAMERA_CREATE );
		
	}

	private void makeNoteByText() {
		MuLog.logi("makeNoteByText start");
		Intent intent = new Intent(getActivity(),TextNoteActivity.class);
		startActivityForResult(intent, CODE_FOR_TEXT_CREATE );
//		openPage("text_create", new TextCreateNoteFragment());
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	
		switch (requestCode) {
		case CODE_FOR_TEXT_CREATE:
			
			break;

		default:
			break;
		}
		
		notifyNoteDatasChange();
	}

	private void notifyNoteDatasChange() {

		mNoteDBData = DBManager.instance(getActivity()).queryAllData();

		mListViewAdapter.setDatasource(getActivity(), mNoteDBData);
		mListViewAdapter.notifyDataSetChanged();
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {
		MuLog.logi("editNoteByText start");
		Intent intent = new Intent(getActivity(),TextNoteActivity.class);
		intent.putExtra("note_bean", mNoteDBData.get(pos));
		startActivityForResult(intent, CODE_FOR_TEXT_CREATE );
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		String[] itemArr = {"删除","分享"};
		showAlertDialog(itemArr,new android.content.DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if(which == 0){
					delNoteItem(which);
				}else if(which == 1){
					shareNoteItem(which);
				}
			}
		});
		return false;
	}

	protected void shareNoteItem(int which) {
		//分享note
		ShareManeger sm  = ShareManeger.instance();
		sm.shareInfo(getActivity());
	}
	
	/**删除 某一个note item */
	protected void delNoteItem(int which) {
		DBManager.instance(getActivity()).deleteOldNote(mNoteDBData.get(which));
		notifyNoteDatasChange();
	}

	
}
