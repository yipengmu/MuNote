/**
 * 
 */
package com.laomu.note.common.weather;

import java.net.URLEncoder;


/**
 * @author luoyuan.myp
 *
 * 2014-3-30
 * 
 * full version: http//m.weather.com.cn/data/101200101.html 
 * 
 * mini version: http://www.weather.com.cn/data/sk/101010100.html
 * 
 * 百度 lbs apkey:37c73c4541273cbafd0b7695d0667a17
 * http://api.map.baidu.com/telematics/v3/weather?location=北京&output=json&ak=37c73c4541273cbafd0b7695d0667a17
 */
public class WeatherController {
	private String cityName;
	private String cityCode;
	private final static String baseBaiduWeatherUrl = "http://api.map.baidu.com/telematics/v3/weather";
	private final static String ak = "37c73c4541273cbafd0b7695d0667a17";
	
	public void getCityWeather(String cityCode){
		
	}
	
	public static String getWeatherUrl(String cityName){
		return baseBaiduWeatherUrl + "?location=" + URLEncoder.encode(cityName) + "&output=json" + "&ak=" + ak ;
	}
}
