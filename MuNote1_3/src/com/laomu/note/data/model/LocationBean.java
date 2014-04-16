package com.laomu.note.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.amap.api.location.AMapLocation;

/**
 * @author luoyuan.myp
 * 
 *         位置model
 * 
 *         2013-7-25
 */
public class LocationBean implements Parcelable {
	/** 经度 */
	private double longtitude;
	/** 纬度 */
	private double latitude;
	/** 省 */
	private String province;
	/** 市 */
	private String city;
	/** 县、区 */
	private String district;
	/** 定位方式 */
	private String provider;
	/** 城市编码 */
	private String cityCode;
	/** 高德 原始 location model */
	private AMapLocation aLocation;
	/** 位置描述 */
	private String desc;

	public static final Parcelable.Creator<LocationBean> CREATOR = new Parcelable.Creator<LocationBean>() {
		// 重写Creator

		@Override
		public LocationBean createFromParcel(Parcel source) {
			LocationBean location = new LocationBean();
			location.longtitude = source.readDouble();
			location.latitude = source.readDouble();
			location.province = source.readString();
			location.city = source.readString();
			location.district = source.readString();
			location.provider = source.readString();
			location.cityCode = source.readString();
			location.aLocation = source.readParcelable(AMapLocation.class.getClassLoader());
			location.desc = source.readString();
			return location;
		}

		@Override
		public LocationBean[] newArray(int size) {
			return null;
		}
	};

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
		return sb.toString();
	}

	public String getDesc() {
		StringBuilder sb = new StringBuilder();

		if (!TextUtils.isEmpty(desc)) {
			sb.append(desc);
		}
		return sb.toString();
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

	public void setDesc(String areDesc) {
		desc = areDesc;
	}

}
