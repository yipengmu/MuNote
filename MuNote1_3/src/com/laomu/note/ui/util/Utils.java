package com.laomu.note.ui.util;

import java.util.Calendar;
import java.util.Date;

import android.os.SystemClock;

/**
 * @author luoyuan.myp
 *
 * 2014-2-17
 */
public class Utils {

	public static String getTimeInfo(Date date) {
		if(date == null){
			return "时间统计出错 --:--";
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);

//		Calendar now = Calendar.getInstance();
//		c.setTimeInMillis(SystemClock.currentThreadTimeMillis());
		StringBuilder sb = new StringBuilder();
//		if(isSameYear(c,now)){
//			
//		}
		sb.append(c.get(Calendar.YEAR) + "年" + (c.get(Calendar.MONTH) + 1) + "月" +c.get(Calendar.DAY_OF_MONTH) + "日");
		sb.append(" ");
		sb.append(c.get(Calendar.HOUR_OF_DAY) +":"+c.get(Calendar.MINUTE));
		return sb.toString() ;
	}

	private static boolean isSameYear(Calendar c, Calendar now) {
		if(c.get(Calendar.YEAR) == now.get(Calendar.YEAR)){
			return true;
		}
		return false;
	}

}
