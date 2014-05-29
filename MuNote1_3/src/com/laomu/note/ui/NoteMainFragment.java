/**
 * 
 */
package com.laomu.note.ui;

import java.util.ArrayList;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.laomu.note.R;
import com.laomu.note.common.MuLog;
import com.laomu.note.data.database.OrmDbManeger;
import com.laomu.note.data.model.NoteBean;
import com.laomu.note.ui.act.TextNoteActivity;
import com.laomu.note.ui.act.camera.CameraNoteActivity;
import com.laomu.note.ui.adapter.NoteListViewAdapter;
import com.laomu.note.ui.base.NoteBaseFragment;
import com.laomu.note.ui.share.ShareManeger;
import com.laomu.note.ui.util.Utils;

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
	private View mView;
	private final int CODE_FOR_TEXT_CREATE = 1;
	private int CODE_FOR_CAMERA_CREATE = 2;
	private View tv_common_head_title;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return mView = inflater.inflate(R.layout.activity_main, container, false);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
		initData();
		initView(savedInstanceState);
		
		//wifi下检查更新
//		考虑到用户流量的限制，目前我们默认在Wi-Fi接入情况下才进行自动提醒。如需要在任意网络环境下都进行更新自动提醒，则请在update调用之前添加以下代码：UmengUpdateAgent.setUpdateOnlyWifi(false)
//		UmengUpdateAgent.update(getActivity());

	}
	
	private void initData() {
		mNoteDBData = (ArrayList<NoteBean>) OrmDbManeger.getInstance().queryNote();
	}


	private void initView(Bundle savedInstanceState) {
		setTitle(mView,R.drawable.ic_menu_user, R.string.app_name, R.drawable.ic_menu_setting);
		findViews();
		initNoteListView();
	}

	
	private void findViews() {
		btn_text_make = mView.findViewById(R.id.btn_text_make);
		btn_camera_make = mView.findViewById(R.id.btn_camera_make);
		tv_common_head_title = mView.findViewById(R.id.tv_common_head_title);
		
		btn_text_make.setOnClickListener(this);
		btn_camera_make.setOnClickListener(this);
		tv_common_head_title.setOnClickListener(this);
		
	}

	private void initNoteListView() {
		mNoteListView = (ListView) mView.findViewById(R.id.lv_note_main);
		mListViewAdapter = new NoteListViewAdapter();
		mListViewAdapter.setDatasource(getActivity(), mNoteDBData);
		mNoteListView.setAdapter(mListViewAdapter);	
		mNoteListView.setSmoothScrollbarEnabled(true);
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

		case R.id.tv_common_head_title:
			scrollTop2NoteList();
			break;
			
		default:
			break;
		}
	}

	private void scrollTop2NoteList() {
		mNoteListView.setSelection(0);
		Toast.makeText(getActivity(), "已回到顶部", Toast.LENGTH_SHORT).show();
	}

	private void makeNoteByCamera() {
		MuLog.logd("makeNoteByCamera start");

		Intent intent = new Intent(getActivity(),CameraNoteActivity.class);
		startActivityForResult(intent, CODE_FOR_CAMERA_CREATE );
		
	}

	private void makeNoteByText() {
		MuLog.logd("makeNoteByText start");
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
		mNoteDBData = (ArrayList<NoteBean>) OrmDbManeger.getInstance().queryNote();
		mListViewAdapter.setDatasource(getActivity(), mNoteDBData);
		mListViewAdapter.notifyDataSetChanged();
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {
		MuLog.logd("editNoteByText start");
		Intent intent = new Intent(getActivity(),TextNoteActivity.class);
		intent.putExtra("note_bean", mNoteDBData.get(pos));
		startActivityForResult(intent, CODE_FOR_TEXT_CREATE );
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1,final int pos, long arg3) {
		String[] itemArr = {"删除","分享"};
		showAlertDialog(itemArr,new android.content.DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if(which == 0){
					delNoteItem(pos);
				}else if(which == 1){
					shareNoteItem(pos);
				}
			}
		});
		return false;
	}

	protected void shareNoteItem(int which) {
		//分享note
		ShareManeger sm  = ShareManeger.instance(getActivity());
		sm.setShareContent(mNoteDBData.get(which).note_content);
		sm.setBitmapUrl(null);
		sm.setBitmapResource(getActivity(),R.drawable.ic_launcher);
		sm.shareInfo(getActivity());
	}
	
	/**删除 某一个note item */
	protected void delNoteItem(int pos) {
		OrmDbManeger.getInstance().delNote(mNoteDBData.get(pos));
		notifyNoteDatasChange();
	}

	
}
