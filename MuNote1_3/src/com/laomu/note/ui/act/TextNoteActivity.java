/**
 * 
 */
package com.laomu.note.ui.act;

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
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.laomu.note.R;
import com.laomu.note.common.CommonDefine;
import com.laomu.note.common.http.HttpMapDefine;
import com.laomu.note.common.http.HttpReqBean;
import com.laomu.note.common.http.excutor.HttpExcutor;
import com.laomu.note.common.http.imp.HttpMethod;
import com.laomu.note.common.http.response.BaiduWeatherResult;
import com.laomu.note.common.lbs.LocationInfoManeger;
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
	private EditText et_text_note;
	private ImageView iv_weather_icon;
	private LocationBean mLocation;
	private BaiduWeatherResult mWeather;
	private TextView mLocationTextViewDesc;
	private TextView mWeatherTextViewDesc;
	private Button mBtnVoiceMakeNote;
	
	private LocationRecevier lbsRecevier = new LocationRecevier();
	private String wUrl = "http://api.map.baidu.com/telematics/v3/weather?location=%E5%8C%97%E4%BA%AC&output=json&ak=37c73c4541273cbafd0b7695d0667a17";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.text_create_note);

		initData();
		initView();
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

	private void initView() {
		setTitle(0, R.string.create_note, 0);
		findViews();
		initLBSServer();
		initWeatherServer();
		initUIWidget();
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
			if (null != mWeather) {
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

				if (location != null) {
					registeWeatherCall(Utils.getWeatherUrl(location.city));
				}
			}
		}, 50);
	}

	private void registeWeatherCall(String url) {
		HttpExcutor ex = new HttpExcutor();
		ex.req(HttpMapDefine.Weather, new HttpReqBean(HttpMethod.get, url, null, mWeatherHandler));
	}

	private void initUIWidget() {
		if (null != mNoteBean) {
			et_text_note.setText("" + mNoteBean.note_content);
		}

		if (mLocation != null) {
			mLocationTextViewDesc.setText(String.valueOf(mLocation.desc));
		}
	}

	private void findViews() {
		et_text_note = (EditText) findViewById(R.id.et_text_note);
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
		String label = et_text_note.getText().toString().trim();
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
		if (mWeather != null) {
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
	 * @param mLocation
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

		default:
			break;
		}
		return false;
	}

	private void makeNoteFromVoice() {
		
	}
}
