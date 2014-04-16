/**
 * 
 */
package com.laomu.note.ui.act;

import java.util.Date;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.animation.AlphaAnimation;
import android.widget.EditText;
import android.widget.TextView;

import com.laomu.note.R;
import com.laomu.note.common.CommonDefine;
import com.laomu.note.common.http.HttpMapDefine;
import com.laomu.note.common.http.HttpReqBean;
import com.laomu.note.common.http.excutor.HttpExcutor;
import com.laomu.note.common.http.imp.HttpMethod;
import com.laomu.note.common.http.response.bean.WeatherBean;
import com.laomu.note.data.database.OrmDbManeger;
import com.laomu.note.data.database.old.DBManager;
import com.laomu.note.data.model.LocationBean;
import com.laomu.note.data.model.NoteBean;
import com.laomu.note.ui.NoteApplication;
import com.laomu.note.ui.base.NoteBaseActivity;
import com.laomu.note.ui.util.Utils;

/**
 * @author luoyuan.myp
 *
 * 2014-2-17
 */
public class TextNoteActivity extends NoteBaseActivity{

	private NoteBean mNoteBean;
	private int MODE_CREATE = 1;
	private int MODE_EDIT = 2;
	private int mMode = 0;
	private EditText et_text_note;

	private LocationBean mLocation;
	private TextView mLocationTextViewDesc;

	private LocationRecevier lbsRecevier = new LocationRecevier();
	
	private Handler mHander = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			WeatherBean res = (WeatherBean)msg.getData().get("res");
			updateWeatherUI(res);
		}
		
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.text_create_note);
		
		initData();
		initView();
	}
	
	protected void updateWeatherUI(WeatherBean res) {
	
	}

	private void initView() {
		setTitle(0,R.string.create_note,0);
		findViews();
		registeLbsLis();
		String wUrl = "http://www.weather.com.cn/data/sk/101010100.html";
		registeWeatherCall(wUrl);
		initUIWidget();
	}

	private void registeWeatherCall(String url) {
//		http://www.weather.com.cn/data/sk/101010100.html
		HttpExcutor ex = new HttpExcutor();
		ex.req(HttpMapDefine.Weather, new HttpReqBean(HttpMethod.get,url, null,mHander));
	}

	private void initUIWidget() {
		if(null != mNoteBean){
			et_text_note.setText("" + mNoteBean.note_content);
		}
		
		if(mLocation != null){
			mLocationTextViewDesc.setText(String.valueOf(mLocation.getDesc()));
		}
	}

	private void findViews() {
		et_text_note = (EditText)findViewById(R.id.et_text_note);		
		mLocationTextViewDesc = (TextView) findViewById(R.id.tv_location_desc);
	}

	/**注册定位回调*/
	private void registeLbsLis() {
		IntentFilter filter = new IntentFilter(CommonDefine.LBS_ACTION);
		registerReceiver(lbsRecevier, filter);
	}

	
	private void initData() {
		mNoteBean = getIntent().getParcelableExtra("note_bean");
		mLocation = NoteApplication.getLBSManeger().getmLocationInfoModel();
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
			if(hasRealData()){
				OrmDbManeger.getInstance().addNote(mNoteBean);
//				DBManager.instance(getApplicationContext()).add(mNoteBean);
			}
		}else{
			OrmDbManeger.getInstance().updateNote(mNoteBean);
//			DBManager.instance(getApplicationContext()).updateAge(mNoteBean);
		}
	}

	private boolean hasRealData() {
		if(mNoteBean != null && !TextUtils.isEmpty(mNoteBean.note_content)){
			return true;
		}
		return false;
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
		Date date = new Date(System.currentTimeMillis());
		mNoteBean.note_time = "" + Utils.getTimeInfo(date);
//		mNoteBean.note_location = mLocation;
	}
	
	class LocationRecevier extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			updateLBSInfo();
		}
		
	}

	/**在主线程中更新ui*/
	public void updateLBSInfo() {
		mLocation = NoteApplication.getLBSManeger().getmLocationInfoModel();
		String locationLabel = mLocation.getDesc();
		String currentLabel = mLocationTextViewDesc.getText().toString();
		if(null != mLocation && !TextUtils.isEmpty(locationLabel) && !locationLabel.equals(currentLabel)){
			mLocationTextViewDesc.setText(mLocation.getDesc());
			AlphaAnimation aam = new AlphaAnimation(0, 1);
			aam.setDuration(2000);
			mLocationTextViewDesc.startAnimation(aam);
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		unRegisteLbsLis();
	}

	private void unRegisteLbsLis() {
		unregisterReceiver(lbsRecevier);
	}
}
