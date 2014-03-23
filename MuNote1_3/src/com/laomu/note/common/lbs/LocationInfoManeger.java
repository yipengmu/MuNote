package com.laomu.note.common.lbs;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;

public class LocationInfoManeger implements AMapLocationListener {

	private LocationInfo mLocation;
	private static LocationInfoImp mLocationInfoImp;
	private static LocationInfoManeger instance;
	private LocationManagerProxy mAMapLocManager = null;
	public static String mTestCityName = "";

	/** 返回LocationInfoManeger 静态实例 */
	public static LocationInfoManeger getInstance() {
		if (instance == null) {
			instance = new LocationInfoManeger();
		}
		return instance;
	}

	/** 设置 位置信息监听器 */
	public void setLocationInfoLisener(LocationInfoImp lisener) {
		instance.mLocationInfoImp = lisener;
	}

	/** 开启定位服务 */
	public void activeLocate(Context c) {
		if (mAMapLocManager == null) {
			mAMapLocManager = LocationManagerProxy.getInstance(c);

		}
		try {
			mAMapLocManager.requestLocationUpdates(
					LocationProviderProxy.AMapNetwork, 5000L, 10F,
					LocationInfoManeger.this);
		} catch (Exception e) {
		}

	}

	/** 暂停 定位服务 */
	public void deactiveLocate() {
		if (mAMapLocManager != null) {
			mAMapLocManager.removeUpdates(this);
			mAMapLocManager.destory();
		}

		mAMapLocManager = null;
	}

	/** 返回地理数据 model 信息 mLocation */
	public LocationInfo getmLocationInfoModel() {
		return instance.mLocation;
	}

	@Override
	public void onLocationChanged(Location arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLocationChanged(AMapLocation aLocation) {
		// TODO 高德定位接口回调成功
		if (aLocation != null) {
			String cityName = aLocation.getCity();

			mLocation = new LocationInfo();
			mLocation.setLongtitude(aLocation.getLongitude());
			mLocation.setLatitude(aLocation.getLatitude());
			mLocation.setProvince(aLocation.getProvince());
			mLocation.setCity(cityName);
			mLocation.setDistrict(aLocation.getDistrict());
			mLocation.setProvider(aLocation.getProvider());
			mLocation.setAmapModel(aLocation);
		}
	}

}
