/**
 * 
 */
package com.laomu.note.common.preferences;

import java.util.Set;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

/**
 * @author luoyuan.myp
 *
 * 2014-3-30
 */
public class PreferenceCenter {

	private SharedPreferences mPref;
	private Editor mEditor;
	private String WEATHER_CITY_NAME_LIST;

	private static PreferenceCenter mInstance = null;

	private PreferenceCenter(Context context) {
		mPref = PreferenceManager.getDefaultSharedPreferences(context);
		mEditor = mPref.edit();
	}

	public static PreferenceCenter getPreferences(Context context) {
		if (null == mInstance) {
			mInstance = new PreferenceCenter(context);
		}

		return mInstance;
	}
	
	public void setCityNames(Set<String> citySet){
		mEditor.putStringSet(WEATHER_CITY_NAME_LIST, citySet);
		mEditor.commit();
	}
	
	public String getCityNames(){
		return mPref.getString(WEATHER_CITY_NAME_LIST, null);
	}
}
