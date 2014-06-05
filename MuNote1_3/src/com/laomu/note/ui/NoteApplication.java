/**
 * 
 */
package com.laomu.note.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.laomu.note.common.lbs.LocationInfoManeger;
import com.laomu.note.common.preferences.PreferenceCenter;
import com.laomu.note.ui.util.Utils;
import com.umeng.analytics.MobclickAgent;

/**
 * @author luoyuan.myp
 *
 * 2014-3-28
 */
public class NoteApplication extends Application{


	private static LocationInfoManeger mLBSManeger = null;
	public static Context appContext = null;
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		initNoteApplication();
		
	}
	private void initNoteApplication() {
		appContext = getApplicationContext();
		initLBS();
//		initWeatherData();
		initPush();
		initUmeng();
	}
	
	private void initUmeng() {
		MobclickAgent.setDebugMode( true );
	}
	private void initPush() {
		Utils.initPush(appContext);
	}
	
	private void initWeatherData() {
		if(TextUtils.isEmpty(PreferenceCenter.getPreferences().getCityNames())){
			try {
				//read data
				InputStream cityNameInput = getAssets().open("cityName.txt");
				InputStreamReader cityNameINR = new InputStreamReader(cityNameInput);
				BufferedReader cityNameBr = new BufferedReader(cityNameINR);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	private void initLBS() {
		mLBSManeger = LocationInfoManeger.getInstance();	
		mLBSManeger.activeLocate(getApplicationContext());
	}

	public static LocationInfoManeger getLBSManeger(){
		return mLBSManeger;
	}
}
