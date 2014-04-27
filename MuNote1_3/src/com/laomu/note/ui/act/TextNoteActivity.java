/**
 * 
 */
package com.laomu.note.ui.act;

import java.util.Date;

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
import android.view.animation.AlphaAnimation;
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
import com.laomu.note.common.http.response.bean.BaiduWeatherBean;
import com.laomu.note.common.weather.WeatherController;
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
public class TextNoteActivity extends NoteBaseActivity {

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

	private LocationRecevier lbsRecevier = new LocationRecevier();
	private String wUrl = "http://api.map.baidu.com/telematics/v3/weather?location=%E5%8C%97%E4%BA%AC&output=json&ak=37c73c4541273cbafd0b7695d0667a17";

	private Handler mWeatherHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			mWeather = (BaiduWeatherResult) msg.getData().get("res");
			if(null != mWeather){
				updateWeatherUI(mWeather.getDefaultBean());
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.text_create_note);

		initData();
		initView();
	}

	protected void updateWeatherUI(final BaiduWeatherBean results) {
		if (null != results) {
			mWeatherTextViewDesc.setText(results.getDefaultData().desc());
			HttpReqBean req = new HttpReqBean(HttpMethod.get,
					results.getDefaultData().dayPictureUrl, null, mWeatherIconHandler);
			new HttpExcutor().req(HttpMapDefine.Bitmap, req);
		}
	}

	private void initView() {
		setTitle(0, R.string.create_note, 0);
		findViews();
		registeLbsLis();
		if(mLocation != null){
			registeWeatherCall(WeatherController.getWeatherUrl(mLocation.city));
		}else{
			new Handler().postDelayed(new Runnable() {

				@Override
				public void run() {

					if (mLocation != null) {
						registeWeatherCall(Utils.getWeatherUrl(mLocation.city));
					}
				}
			}, 500);
		}
		initUIWidget();
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
	}

	/** 注册定位回调 */
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
			updateLBSInfo();
		}

	}

	/** 在主线程中更新ui */
	public void updateLBSInfo() {
		mLocation = NoteApplication.getLBSManeger().getmLocationInfoModel();
		String locationLabel = mLocation.desc;
		String currentLabel = mLocationTextViewDesc.getText().toString();
		if (null != mLocation && !TextUtils.isEmpty(locationLabel)
				&& !locationLabel.equals(currentLabel)) {
			mLocationTextViewDesc.setText(mLocation.desc);
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
