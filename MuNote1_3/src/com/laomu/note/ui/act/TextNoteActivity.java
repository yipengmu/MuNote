/**
 * 
 */
package com.laomu.note.ui.act;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.speech.RecognizerIntent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.laomu.note.R;
import com.laomu.note.common.CommonDefine;
import com.laomu.note.common.http.HttpMapDefine;
import com.laomu.note.common.http.HttpReqBean;
import com.laomu.note.common.http.excutor.HttpExcutor;
import com.laomu.note.common.http.imp.HttpMethod;
import com.laomu.note.common.http.response.BaiduWeatherResult;
import com.laomu.note.common.lbs.LocationInfoManeger;
import com.laomu.note.common.speech.SpeechManeger;
import com.laomu.note.data.database.OrmDbManeger;
import com.laomu.note.data.model.LocationBean;
import com.laomu.note.data.model.NoteBean;
import com.laomu.note.ui.NoteApplication;
import com.laomu.note.ui.base.NoteBaseActivity;
import com.laomu.note.ui.util.Utils;

/**
 * @author luoyuan.myp
 * 
 *         2014-2-17
 */
public class TextNoteActivity extends NoteBaseActivity implements OnClickListener, OnLongClickListener {

	private NoteBean mNoteBean;
	private int MODE_CREATE = 1;
	private int MODE_EDIT = 2;
	private int mMode = 0;
	private EditText mEditNotecontent;
	private ImageView iv_weather_icon;
	private LocationBean mLocation;
	private BaiduWeatherResult mWeather;
	private TextView mLocationTextViewDesc;
	private TextView mWeatherTextViewDesc;
	private Button mBtnVoiceMakeNote;
	private SpeechManeger mSpeechManeger;
	private LocationRecevier lbsRecevier = new LocationRecevier();
	private String mSpeechMsgResult = "";
	private static final int REQUEST_CODE_SEARCH = 1099;
	private long originalLbsUpdateTimeStamp = 0;
	private boolean bDefaultWeatherInfoloaded = false;
	
	private Handler mSpeechMsgHander  = new Handler(){
		public void handleMessage(Message msg) {
			mSpeechMsgResult = "";
			if(msg.what == SpeechManeger.MSG_RESPONSE){
				Bundle bundle = msg.getData();
				mSpeechMsgResult = bundle.getString("msg");
				appendNoteContent(mSpeechMsgResult);
			}
			
		};
	};
	private MenuItem mMenuItem;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.text_create_note);

		initData();
		initView();
	}

	protected void appendNoteContent(String msgInfo) {
		if(mEditNotecontent == null || TextUtils.isEmpty(msgInfo)){
			return;
		}
		mEditNotecontent.append("\n" + msgInfo);
	}

	private void initView() {
		setTitle(0, R.string.create_note, 0);
		initToolbar();
		findViews();
		initLBSServer();
		initWeatherServer();
		initUIWidget();
		initSpeech();
	}

	private void initToolbar() {
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		// App Logo
		toolbar.setLogo(R.drawable.ic_launcher);
		// Title
		toolbar.setTitle("便利贴");
		// Sub Title
		toolbar.setSubtitle("返回自动保存");
		// Menu item click 的監聽事件一樣要設定在 setSupportActionBar 才有作用
		toolbar.setOnMenuItemClickListener(onMenuItemClick);
	}

	private void initSpeech() {
		mSpeechManeger = SpeechManeger.getInstance(this);
		mSpeechManeger.init();
		mSpeechManeger.setMsgHandler(mSpeechMsgHander);
	}

	private void initLBSServer() {
		if (mNoteBean != null) {
			// 已有的便利贴，可能是从noteList或者从地图 marker上跳转过来的
			if (mNoteBean.note_location_id <= 0) {
				// 之前的noteBean没有位置信息
				registeLbsLis();
			} else {
				// 之前的noteBean有位置信息，就当前noteBean的定位地址信息
				List<LocationBean> loc = OrmDbManeger.getInstance().queryLocation(
						mNoteBean.note_location_id);
				if (loc.size() > 0) {
					updateLBSInfo(loc.get(0));
				}
			}
		} else {
			// 新打开的便利贴
			registeLbsLis();
		}
	}

	private void initWeatherServer() {
		if (mNoteBean != null && TextUtils.isEmpty(mNoteBean.note_weather_info)) {

		} else {
			if (mLocation != null) {
				registeWeatherCall(Utils.getWeatherUrl(mLocation.city));
			} else {
				mLocation = LocationInfoManeger.getInstance().getmLocationInfoModel();
				startRequestWeatherInfo(mLocation);
			}
		}

	}

	private Handler mWeatherHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			mWeather = (BaiduWeatherResult) msg.getData().get("res");
			logd(" weather result: " + Utils.getJsonString(mWeather));
			if (null != mWeather && null != mWeather.getDefaultBean() && null != mWeather.getDefaultBean().getDefaultData()) {
				updateWeatherUI(mWeather.getDefaultBean().getDefaultData().desc(), mWeather
						.getDefaultBean().getDefaultData().dayPictureUrl);
			}
		}

	};
	private Handler mWeatherIconHandler = new Handler() {
		public void handleMessage(Message msg) {
			Bitmap bitmap = (Bitmap) msg.getData().get("res");
			iv_weather_icon.setImageBitmap(bitmap);
			iv_weather_icon.setVisibility(View.VISIBLE);
		};
	};
	
	private void startRequestWeatherInfo(final LocationBean location) {
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {

				if (location != null && needUpdateDataFromLbsUpdate()) {
					registeWeatherCall(Utils.getWeatherUrl(location.city));
				}
			}
		}, 50);
	}

	protected boolean needUpdateDataFromLbsUpdate() {
		if(bDefaultWeatherInfoloaded == false){
			bDefaultWeatherInfoloaded = true;
			return true;
		}
		
		long currentTime = System.currentTimeMillis();
		if(currentTime - originalLbsUpdateTimeStamp > 1000 * 60 *5){
			originalLbsUpdateTimeStamp = currentTime;
			return true;
		}
		return false;
	}

	protected void updateWeatherUI(final String weatherInfo, String dayPictureUrl) {
		if (null != weatherInfo) {
			mWeatherTextViewDesc.setText(weatherInfo);
			AlphaAnimation aam = new AlphaAnimation(0, 1);
			aam.setDuration(2000);
			mWeatherTextViewDesc.startAnimation(aam);
			HttpReqBean req = new HttpReqBean(HttpMethod.get, dayPictureUrl, null,
					mWeatherIconHandler);
			new HttpExcutor().req(HttpMapDefine.Bitmap, req);
		}
	}

	private void registeWeatherCall(String url) {
		HttpExcutor ex = new HttpExcutor();
		ex.req(HttpMapDefine.Weather, new HttpReqBean(HttpMethod.get, url, null, mWeatherHandler));
		logd("weather url :" + url);
	}

	private void initUIWidget() {
		if (null != mNoteBean) {
			mEditNotecontent.setText("" + mNoteBean.note_content);
		}

		if (mLocation != null) {
			mLocationTextViewDesc.setText(String.valueOf(mLocation.desc));
		}
	}

	private void findViews() {
		mEditNotecontent = (EditText) findViewById(R.id.et_text_note);
		mLocationTextViewDesc = (TextView) findViewById(R.id.tv_location_desc);
		mWeatherTextViewDesc = (TextView) findViewById(R.id.tv_weather_desc);
		iv_weather_icon = (ImageView) findViewById(R.id.iv_weather_icon);
		mBtnVoiceMakeNote = (Button) findViewById(R.id.btn_voice_make_text);
		mBtnVoiceMakeNote.setOnClickListener(this);
		mBtnVoiceMakeNote.setOnLongClickListener(this);
	}

	/** 注册定位回调 */
	private void registeLbsLis() {
		IntentFilter filter = new IntentFilter(CommonDefine.LBS_ACTION);
		registerReceiver(lbsRecevier, filter);
	}

	private void initData() {
		mNoteBean = getIntent().getParcelableExtra("note_bean");
		originalLbsUpdateTimeStamp = System.currentTimeMillis();
		initMode();
	}

	private void initMode() {
		if (null == mNoteBean) {
			mMode = MODE_CREATE;
		} else {
			mMode = MODE_EDIT;
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		saveNote();
	}

	private void saveNote() {
		// save content
		saveNoteContent();
		if (mMode == MODE_CREATE) {
			if (hasRealData()) {
				OrmDbManeger.getInstance().addNote(mNoteBean);
			}
		} else {
			OrmDbManeger.getInstance().updateNote(mNoteBean);
		}
	}

	private boolean hasRealData() {
		if (mNoteBean != null && !TextUtils.isEmpty(mNoteBean.note_content)) {
			return true;
		}
		return false;
	}

	private void saveNoteContent() {
		String label = mEditNotecontent.getText().toString().trim();
		String labelTitle = label;

		if (null == mNoteBean) {
			mNoteBean = new NoteBean();
		}
		mNoteBean.note_content = label;
		if (!TextUtils.isEmpty(labelTitle) && labelTitle.length() > 10) {
			labelTitle = labelTitle.substring(0, 10);
		}
		mNoteBean.note_title = labelTitle;
		Date date = new Date(System.currentTimeMillis());
		mNoteBean.note_time = "" + Utils.getTimeInfo(date);
		// 地理位置外键
		if (mLocation != null) {
			mNoteBean.note_location_id = Utils.getLocationIdFromDB(mLocation);
		} else {
			mNoteBean.note_location_id = 0;
		}
		// 天气
		if (null !=mWeather  && null != mWeather.getDefaultBean() && null != mWeather.getDefaultBean().getDefaultData()) {
			mNoteBean.note_weather_temp = mWeather.getDefaultBean().getDefaultData().temperature;
			mNoteBean.note_weather_info = mWeather.getDefaultBean().getDefaultData().desc();
		} else {
			mNoteBean.note_weather_temp = "";
			mNoteBean.note_weather_info = "";
		}
	}

	class LocationRecevier extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			mLocation = NoteApplication.getLBSManeger().getmLocationInfoModel();
			updateLBSInfo(mLocation);
		}

	}

	/**
	 * 在主线程中更新ui
	 * 
	 * @param location
	 */
	public void updateLBSInfo(LocationBean location) {
		if (location == null) {
			return;
		}
		String locationLabel = location.desc;
		String currentLabel = mLocationTextViewDesc.getText().toString();
		if (!TextUtils.isEmpty(locationLabel) && !locationLabel.equals(currentLabel)) {
			mLocationTextViewDesc.setText(location.desc);
			AlphaAnimation aam = new AlphaAnimation(0, 1);
			aam.setDuration(2000);
			mLocationTextViewDesc.startAnimation(aam);
		}

		// 启动基于位置的天气信息请求
		startRequestWeatherInfo(location);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unRegisteLbsLis();
	}

	private void unRegisteLbsLis() {
		try {
			unregisterReceiver(lbsRecevier);
		} catch (Exception e) {
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_voice_make_text:
			//语音输入
			toast("长按试试哈");
			break;

		default:
			break;
		}
	}

	@Override
	public boolean onLongClick(View v) {
		switch (v.getId()) {
		case R.id.btn_voice_make_text:
			//语音输入
			makeNoteFromVoice();
			break;
		}
		return false;
	}

	private void makeNoteFromVoice() {
		mSpeechManeger.showSpeechDialog();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == REQUEST_CODE_SEARCH && resultCode == RESULT_OK)
		{
			// 取得识别的字符串
			ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
			String res = results.get(0);
			mEditNotecontent.append("\n" + res);			
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.menu_note_main, menu);
//		mMenuItem = menu.findItem(R.id.menu_name);
//		mMenuItem.setTitle(getResources().getString(R.string.app_name));

		return super.onCreateOptionsMenu(menu);
	}

	private Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener() {
		@Override
		public boolean onMenuItemClick(MenuItem menuItem) {
			String msg = "";
			switch (menuItem.getItemId()) {
				case R.id.action_edit:
					msg += "Click edit";
					break;
				case R.id.action_share:
					msg += "Click share";
					break;
				case R.id.action_settings:
					msg += "Click setting";
					break;
			}

			if(!msg.equals("")) {
				Toast.makeText(TextNoteActivity.this, msg, Toast.LENGTH_SHORT).show();
			}
			return true;
		}
	};

}
