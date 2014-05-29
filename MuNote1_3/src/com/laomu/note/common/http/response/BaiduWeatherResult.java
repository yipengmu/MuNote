/**
 * 
 */
package com.laomu.note.common.http.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.laomu.note.data.bean.BaiduWeatherBean;

/**
 * @author luoyuan.myp
 * 
 {
    "error":0,
    "status":"success",
    "date":"2014-04-27",
    "results":[
        {
            "currentCity":"\u5317\u4eac",
            "weather_data":[
                {
                    "date":"\u5468\u65e5(\u4eca\u5929, \u5b9e\u65f6\uff1a20\u2103)",
                    "dayPictureUrl":"http:\/\/api.map.baidu.com\/images\/weather\/day\/duoyun.png",
                    "nightPictureUrl":"http:\/\/api.map.baidu.com\/images\/weather\/night\/qing.png",
                    "weather":"\u591a\u4e91\u8f6c\u6674",
                    "wind":"\u5fae\u98ce",
                    "temperature":"26 ~ 10\u2103"
                },
                {
                    "date":"\u5468\u4e00",
                    "dayPictureUrl":"http:\/\/api.map.baidu.com\/images\/weather\/day\/qing.png",
                    "nightPictureUrl":"http:\/\/api.map.baidu.com\/images\/weather\/night\/qing.png",
                    "weather":"\u6674",
                    "wind":"\u5fae\u98ce",
                    "temperature":"26 ~ 11\u2103"
                },
                {
                    "date":"\u5468\u4e8c",
                    "dayPictureUrl":"http:\/\/api.map.baidu.com\/images\/weather\/day\/qing.png",
                    "nightPictureUrl":"http:\/\/api.map.baidu.com\/images\/weather\/night\/qing.png",
                    "weather":"\u6674",
                    "wind":"\u5fae\u98ce",
                    "temperature":"28 ~ 13\u2103"
                },
                {
                    "date":"\u5468\u4e09",
                    "dayPictureUrl":"http:\/\/api.map.baidu.com\/images\/weather\/day\/qing.png",
                    "nightPictureUrl":"http:\/\/api.map.baidu.com\/images\/weather\/night\/duoyun.png",
                    "weather":"\u6674\u8f6c\u591a\u4e91",
                    "wind":"\u5fae\u98ce",
                    "temperature":"29 ~ 16\u2103"
                }
            ]
        }
    ]
}
 * 
 * 
 * 
 * 2014-3-30
 */
public class BaiduWeatherResult implements Parcelable{
	
	private String error;
	private String status;
	private String date;
	public BaiduWeatherBean[] results;

	public BaiduWeatherBean getDefaultBean(){
		if(null != results && results.length >=0){
			return results[0];
		}
		return null;
	}
	
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(error);
		dest.writeString(status);
		dest.writeString(date);
		dest.writeArray(results);
	}
	
	public static final Parcelable.Creator<BaiduWeatherResult> CREATOR = new Parcelable.Creator<BaiduWeatherResult>() {
		// 重写Creator

		@Override
		public BaiduWeatherResult createFromParcel(Parcel source) {
			BaiduWeatherResult bean = new BaiduWeatherResult();
			bean.error = source.readString();
			bean.status = source.readString();
			bean.date = source.readString();
			bean.results = (BaiduWeatherBean[]) source.readArray(BaiduWeatherBean.class.getClassLoader());
			return bean;
		}

		@Override
		public BaiduWeatherResult[] newArray(int size) {
			return null;
		}
	};
}
