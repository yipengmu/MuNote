/**
 * 
 */
package com.laomu.note.common.http.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.laomu.note.data.bean.FullWeatherBean;

/**
 * @author luoyuan.myp
 *
 * 2014-4-17
 */
public class WeatherResult implements Parcelable{
	public FullWeatherBean weatherinfo;

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
