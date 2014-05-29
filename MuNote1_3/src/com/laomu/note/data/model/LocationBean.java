package com.laomu.note.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DatabaseField;

/**
 * @author luoyuan.myp
 * 
 *         位置model
 * `locationbean` (`city` ,`cityCode` ,`desc` ,`district` ,`province` ,`provider` ,`longtitude` ,`latitude` ) 
 *         2013-7-25
 */
public class LocationBean 
//implements Parcelable
{
	@DatabaseField(generatedId=true)
	public int id = 0;
//	/** 市 */
	@DatabaseField
	public String city = "";
//	/** 城市编码 */
	@DatabaseField
	public String cityCode = "";
//	/** 位置描述 */
	@DatabaseField
	public String desc = "";
//	/** 县、区 */
	@DatabaseField
	public String district = "";
//	/** 省 */
	@DatabaseField
	public String province = "";
//	/** 定位方式 */
	@DatabaseField
	public String provider = "";
//	/** 经度 */
	@DatabaseField
	public String longtitude = "";
//	/** 纬度 */
	@DatabaseField
	public String latitude = "";

	public LocationBean(){
		
	}
	
	public static final Parcelable.Creator<LocationBean> CREATOR = new Parcelable.Creator<LocationBean>() {
		// 重写Creator

		@Override
		public LocationBean createFromParcel(Parcel source) {
			LocationBean location = new LocationBean();
			location.city = source.readString();
			location.cityCode = source.readString();
			location.desc = source.readString();
			location.district = source.readString();
			location.province = source.readString();
			location.provider = source.readString();
			location.longtitude = source.readString();
			location.latitude = source.readString();
			return location;
		}

		@Override
		public LocationBean[] newArray(int size) {
			return null;
		}
	};

//	public String getLongtitude() {
//		return longtitude;
//	}
//
//	public void setLongtitude(String longtitude) {
//		this.longtitude = longtitude;
//	}
//
//	public String getLatitude() {
//		return latitude;
//	}
//
//	public void setLatitude(String latitude) {
//		this.latitude = latitude;
//	}
//
//	public String getProvince() {
//		return province;
//	}
//
//	public void setProvince(String province) {
//		this.province = province;
//	}
//
//	public String getCity() {
//		return city;
//	}
//
//	public void setCity(String city) {
//		this.city = city;
//	}
//
//	public String getDistrict() {
//		return district;
//	}
//
//	public void setDistrict(String district) {
//		this.district = district;
//	}
//
//	public String getProvider() {
//		return provider;
//	}
//
//	public void setProvider(String provider) {
//		this.provider = provider;
//	}
//
//	public String getCityCode() {
//		return cityCode;
//	}
//
//	public void setCityCode(String cityCode) {
//		this.cityCode = cityCode;
//	}

/*	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(longtitude + ",");
		sb.append(latitude + ",");
		sb.append(province + ",");
		sb.append(city + ",");
		sb.append(district + ",");
		sb.append(provider + ",");
		sb.append(cityCode + ",");
		return sb.toString();
	}
*/
//	public String getDesc() {
//		StringBuilder sb = new StringBuilder();
//
//		if (!TextUtils.isEmpty(desc)) {
//			sb.append(desc);
//		}
//		return sb.toString();
//	}
//
//	public void setDesc(String areDesc) {
//		desc = areDesc;
//	}

//	@Override
//	public int describeContents() {
//		return 0;
//	}
//
//	@Override
//	public void writeToParcel(Parcel dest, int flags) {
//		dest.writeString(city);
//		dest.writeString(cityCode);
//		dest.writeString(desc);
//		dest.writeString(district);
//		dest.writeString(province);
//		dest.writeString(provider);
//		dest.writeString(longtitude);
//		dest.writeString(latitude);
//	}

}
