package com.laomu.note.common.lbs;

import android.os.Parcel;
import android.os.Parcelable;

import com.amap.api.location.AMapLocation;


/**
 * @author luoyuan.myp
 * 
 * 位置model
 * 
 * 2013-7-25
 */
public class LocationInfo implements Parcelable {
	/**经度*/
	private double longtitude;
	/**纬度*/
	private double latitude;
	/**省*/
	private String province;
	/**市*/
	private String city;
	/**县、区*/
	private String district;
	/**定位方式*/
	private String provider;
	/**阿里 内部 城市三字码*/
	private String cityCode;
	/**高德 原始 location model*/
	private AMapLocation aLocation;
	
	public AMapLocation getAMapLocation() {
		return aLocation;
	}
	public double getLongtitude() {
		return longtitude;
	}
	public void setLongtitude(double longtitude) {
		this.longtitude = longtitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getProvider() {
		return provider;
	}
	public void setProvider(String provider) {
		this.provider = provider;
	}
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(longtitude + ",");
		sb.append(latitude + ",");
		sb.append(province + ",");
		sb.append(city + ",");
		sb.append(district + ",");
		sb.append(provider + ",");
		sb.append(cityCode + ",");
		return sb.toString() ;
	}
	
	public String getDesc(){
		StringBuilder sb = new StringBuilder();
		sb.append(province + ",");
		sb.append(city + ",");
		sb.append(district + ",");
		return sb.toString() ;
	}
	public void setAmapModel(AMapLocation aLocation) {
		this.aLocation = aLocation;
	}
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		
	}
	
}
