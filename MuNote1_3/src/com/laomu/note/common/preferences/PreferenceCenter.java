/**
 *
 */
package com.laomu.note.common.preferences;

import java.util.Set;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.laomu.note.ui.NoteApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author luoyuan.myp
 *         <p/>
 *         2014-3-30
 */
public class PreferenceCenter {

    private SharedPreferences mPref;
    private Editor mEditor;
    private String WEATHER_CITY_NAME_LIST = "weather_city_name_list";
    private String LOCAL_CACHE_URL_JSONED = "local_cache_url_jsoned";
    private String DOODDLE_MASKER_HAS_SHOWED = "dooddle_masker_has_showed";
    private String SEAL_TEXT_CONTENT = "seal_text_content";


    private static PreferenceCenter mInstance = null;

    private PreferenceCenter(Context context) {
        mPref = PreferenceManager.getDefaultSharedPreferences(context);
        mEditor = mPref.edit();
    }

    public static PreferenceCenter getPreferences() {
        if (null == mInstance) {
            mInstance = new PreferenceCenter(NoteApplication.appContext);
        }

        return mInstance;
    }

    public void setCityNames(Set<String> citySet) {
        mEditor.putStringSet(WEATHER_CITY_NAME_LIST, citySet);
        mEditor.commit();
    }

    public String getCityNames() {
        return mPref.getString(WEATHER_CITY_NAME_LIST, null);
    }

    public void setUrlLocalCacheMap(String json) {
        mEditor.putString(LOCAL_CACHE_URL_JSONED, json);
        mEditor.commit();
    }

    public String getUrlLocalCacheMap() {
        return mPref.getString(LOCAL_CACHE_URL_JSONED, null);
    }

    public boolean getDoodleFirstShowed() {
        return mPref.getBoolean(DOODDLE_MASKER_HAS_SHOWED, false);
    }


    public void setDoodleFirstShowed() {
        mEditor.putBoolean(DOODDLE_MASKER_HAS_SHOWED, true);
        mEditor.commit();
    }


    public void addSealContentHistory(String textContent) {
        if (TextUtils.isEmpty(textContent)) {
            return;
        }
        JSONArray jo = new JSONArray();

        jo.put(textContent);
        mEditor.putString(SEAL_TEXT_CONTENT, jo.toString());
        mEditor.apply();
    }

    public JSONArray getSealContentHistory() {
        JSONArray jo = new JSONArray();
        return jo.put(mPref.getString(SEAL_TEXT_CONTENT, ""));
    }
}
