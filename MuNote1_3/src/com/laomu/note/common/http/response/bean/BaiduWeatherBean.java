/**
 * 
 */
package com.laomu.note.common.http.response.bean;

/**
 * @author luoyuan.myp
 *
 * 2014-4-27
 */
public class BaiduWeatherBean {
	public String currentCity;
	public BWeatherData[] weather_data;
	
	public BWeatherData getDefaultData(){
		if(weather_data.length >= 0){
			return weather_data[0];
		}
		return null;
	}
	/**
	 * 
	 *  {
                    "date":"\u5468\u65e5(\u4eca\u5929, \u5b9e\u65f6\uff1a20\u2103)",
                    "dayPictureUrl":"http:\/\/api.map.baidu.com\/images\/weather\/day\/duoyun.png",
                    "nightPictureUrl":"http:\/\/api.map.baidu.com\/images\/weather\/night\/qing.png",
                    "weather":"\u591a\u4e91\u8f6c\u6674",
                    "wind":"\u5fae\u98ce",
                    "temperature":"26 ~ 10\u2103"
                }
	 * */
	public class BWeatherData{
		public String date;
		public String dayPictureUrl;
		public String nightPictureUrl;
		public String weather;
		public String wind;
		public String temperature;
		
		public String desc(){
			return weather + " [" + temperature + "] " + wind;
		}
	}
}
