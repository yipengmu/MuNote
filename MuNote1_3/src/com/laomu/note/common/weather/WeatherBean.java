/**
 * 
 */
package com.laomu.note.common.weather;

/**
 * @author luoyuan.myp
 *{"weatherinfo":{"city":"北京","cityid":"101010100","temp":"24","WD":"东风","WS":"2级",
 *  "SD":"11%","WSE":"2","time":"15:45","isRadar":"1","Radar":"JC_RADAR_AZ9010_JB"}}
 * 2014-3-30
 */
public class WeatherBean {
	/**城市名 北京*/
	private String city;
	/**城市id 101010100*/
	private String cityid;
	/**城市温度 摄氏度 25*/
	private String temp;
	/**城市风向 东风*/
	private String WD;
	/**城市风的级数 2级*/
	private String WS;
	/**城市湿度 11%*/
	private String SD;
	/**城市wse*/
	private String WSE;
	/**城市天气更新时间*/
	private String time;

	public String desc() {
		return city + ": 气温" +temp + " " +WD +WS + " 湿度 "+SD;
	}
	
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCityid() {
		return cityid;
	}
	public void setCityid(String cityid) {
		this.cityid = cityid;
	}
	public String getTemp() {
		return temp;
	}
	public void setTemp(String temp) {
		this.temp = temp;
	}
	public String getWD() {
		return WD;
	}
	public void setWD(String wD) {
		WD = wD;
	}
	public String getWS() {
		return WS;
	}
	public void setWS(String wS) {
		WS = wS;
	}
	public String getSD() {
		return SD;
	}
	public void setSD(String sD) {
		SD = sD;
	}
	public String getWSE() {
		return WSE;
	}
	public void setWSE(String wSE) {
		WSE = wSE;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
	
	
}
