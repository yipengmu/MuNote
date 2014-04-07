/**
 * 
 */
package com.laomu.note.data.model;

/**
 * @author luoyuan.myp
 *
 * 2014-4-7
 */
public class CityBean {
	private String CityName;
	private String CityCode;
	
	public CityBean (String name ,String code){
		CityName = name;
		CityCode = code;
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
