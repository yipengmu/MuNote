/**
 * 
 */
package com.laomu.note.data.model;

import java.util.Locale;

import android.text.TextUtils;

import com.laomu.note.ui.util.PinyinUtil;

/**
 * @author luoyuan.myp
 *
 * 2014-4-7
 */
public class CityBean {
	private String CityName;
	private String CityCode;
	private String CityPinyinStart;
	
	public CityBean (String name ,String code,String pinyinStart){
		CityName = name;
		CityCode = code;
		CityPinyinStart = pinyinStart;
	}
	
	public CityBean (String name ,String code){
		CityName = name;
		CityCode = code;
	}
	
	public String getCityPinyinStart() {
		if(TextUtils.isEmpty(CityPinyinStart)){
			//调用pingyin4j.jar
			String[] headChar = PinyinUtil.getHeadByString(CityName);
			if(headChar.length > 0){
				char[] letter = headChar[0].toCharArray();
				return String.valueOf(letter, 0, 1).toUpperCase(Locale.getDefault());
			}
			return null;
		}
		return CityPinyinStart;
	}


	public void setCityPinyinStart(String cityPinyinStart) {
		CityPinyinStart = cityPinyinStart;
	}


	public String getCityName() {
		return CityName;
	}
	public void setCityName(String cityName) {
		CityName = cityName;
	}
	public String getCityCode() {
		return CityCode;
	}
	public void setCityCode(String cityCode) {
		CityCode = cityCode;
	}
	
}
