/**
 * 
 */
package com.laomu.note.common.http.response.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author luoyuan.myp
 * 
 * {
    "weatherinfo": {
        "city": "房山",
        "city_en": "fangshan",
        "date_y": "2014年3月4日",
        "date": "",
        "week": "星期二",
        "fchh": "11",
        "cityid": "101011200",
        "temp1": "9℃~-3℃",
        "temp2": "8℃~-3℃",
        "temp3": "7℃~-3℃",
        "temp4": "8℃~-2℃",
        "temp5": "10℃~0℃",
        "temp6": "10℃~1℃",
        "tempF1": "48.2℉~26.6℉",
        "tempF2": "46.4℉~26.6℉",
        "tempF3": "44.6℉~26.6℉",
        "tempF4": "46.4℉~28.4℉",
        "tempF5": "50℉~32℉",
        "tempF6": "50℉~33.8℉",
        "weather1": "晴",
        "weather2": "晴",
        "weather3": "晴",
        "weather4": "晴转多云",
        "weather5": "多云",
        "weather6": "多云",
        "img1": "0",
        "img2": "99",
        "img3": "0",
        "img4": "99",
        "img5": "0",
        "img6": "99",
        "img7": "0",
        "img8": "1",
        "img9": "1",
        "img10": "99",
        "img11": "1",
        "img12": "99",
        "img_single": "0",
        "img_title1": "晴",
        "img_title2": "晴",
        "img_title3": "晴",
        "img_title4": "晴",
        "img_title5": "晴",
        "img_title6": "晴",
        "img_title7": "晴",
        "img_title8": "多云",
        "img_title9": "多云",
        "img_title10": "多云",
        "img_title11": "多云",
        "img_title12": "多云",
        "img_title_single": "晴",
        "wind1": "北风4-5级转微风",
        "wind2": "微风",
        "wind3": "微风",
        "wind4": "微风",
        "wind5": "微风",
        "wind6": "微风",
        "fx1": "北风",
        "fx2": "微风",
        "fl1": "4-5级转小于3级",
        "fl2": "小于3级",
        "fl3": "小于3级",
        "fl4": "小于3级",
        "fl5": "小于3级",
        "fl6": "小于3级",
        "index": "冷",
        "index_d": "天气冷，建议着棉服、羽绒服、皮夹克加羊毛衫等冬季服装。年老体弱者宜着厚棉衣、冬大衣或厚羽绒服。",
        "index48": "冷",
        "index48_d": "天气冷，建议着棉服、羽绒服、皮夹克加羊毛衫等冬季服装。年老体弱者宜着厚棉衣、冬大衣或厚羽绒服。",
        "index_uv": "中等",
        "index48_uv": "中等",
        "index_xc": "较适宜",
        "index_tr": "一般",
        "index_co": "较舒适",
        "st1": "8",
        "st2": "-3",
        "st3": "8",
        "st4": "0",
        "st5": "7",
        "st6": "-1",
        "index_cl": "不宜",
        "index_ls": "基本适宜",
        "index_ag": "易发"
    }
}
 * 
 * 
 * http://m.weather.com.cn/data/101011200.html
 */
public class FullWeatherBean implements Parcelable{
	
	/**城市名 房山*/
	public String city;
	/**城市pinyin fangshang*/
	public String city_en;
	/**2014年3月4日*/
	public String date_y;
	/**东风*/
	public String date;
	/**星期二*/
	public String week;
	/**101011200*/
	public String cityid;
	/**9℃~-3℃*/
	public String temp1;
	/**8℃~-3℃*/
	public String temp2;
	/**10℃~1℃*/
	public String temp3;
	/**10℃~1℃*/
	public String temp4;
	/**10℃~1℃*/
	public String temp5;
	/**10℃~1℃*/
	public String temp6;
	/**48.2℉~26.6℉*/
	public String tempF1;
	/**48.2℉~26.6℉*/
	public String tempF2;
	/**48.2℉~26.6℉*/
	public String tempF3;
	/**48.2℉~26.6℉*/
	public String tempF4;
	/**48.2℉~26.6℉*/
	public String tempF5;
	/**48.2℉~26.6℉*/
	public String tempF6;
	/**晴*/
	public String weather1;
	/**晴*/
	public String weather2;
	/**晴*/
	public String weather3;
	/**晴*/
	public String weather4;
	/**晴*/
	public String weather5;
	/**晴*/
	public String weather6;
	public String img1;
	public String img2;
	public String img3;
	public String img4;
	public String img5;
	public String img6;
	public String img7;
	public String img8;
	public String img9;
	public String img10;
	public String img11;
	public String img12;
	public String img_single;
	/**晴*/
	public String img_title1;
	/**晴*/
	public String img_title2;
	/**晴*/
	public String img_title3;
	/**晴*/
	public String img_title4;
	/**晴*/
	public String img_title5;
	/**晴*/
	public String img_title6;
	/**晴*/
	public String img_title7;
	/**晴*/
	public String img_title8;
	/**晴*/
	public String img_title9;
	/**晴*/
	public String img_title10;
	/**晴*/
	public String img_title11;
	/**晴*/
	public String img_title12;
	/**晴*/
	public String img_title_single;
	/**北风4-5级转微风*/
	public String wind1;
	/**北风4-5级转微风*/
	public String wind2;
	/**北风4-5级转微风*/
	public String wind3;
	/**北风4-5级转微风*/
	public String wind4;
	/**北风4-5级转微风*/
	public String wind5;
	/**北风4-5级转微风*/
	public String wind6;
	/**北风*/
	public String fx1;
	public String fx2;
	/**4-5级转小于3级*/
	public String fl1;
	/**4-5级转小于3级*/
	public String fl2;
	/**4-5级转小于3级*/
	public String fl3;
	/**4-5级转小于3级*/
	public String fl4;
	/**4-5级转小于3级*/
	public String fl5;
	/**4-5级转小于3级*/
	public String fl6;
	/**冷*/
	public String index;
	/**天气冷，建议着棉服、羽绒服、皮夹克加羊毛衫等冬季服装。年老体弱者宜着厚棉衣、冬大衣或厚羽绒服。*/
	public String index_d;
	public String index48;
	public String index48_d;
	/**中等*/
	public String index_uv;
	/**较适宜*/
	public String index_xc;
	public String index_tr;
	public String index_co;
	public String st1;
	public String st2;
	public String st3;
	public String st4;
	public String st5;
	public String st6;
	public String index_cl;
	public String index_ls;
	public String index_ag;
	

	public String desc() {
		return city + ": "+ weather1 + " [" +temp1 + "] " + index;
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
