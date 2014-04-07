package com.laomu.note.ui.util;

import java.util.Calendar;
import java.util.Date;

import com.laomu.note.common.MuLog;
import com.laomu.note.ui.NoteApplication;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.SystemClock;
import android.widget.Toast;

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
	
	public static boolean isNetReady() {
	    ConnectivityManager connMgr = (ConnectivityManager) 
	        NoteApplication.appContext.getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
	    if (networkInfo != null && networkInfo.isConnected()) {
	        // fetch data
	    	return true;
	    } else {
	    	return false;
	    }
	}
	
	public static void toast(String toastInfo){
		Toast.makeText(NoteApplication.appContext, toastInfo, Toast.LENGTH_SHORT).show();
	}
	
	public static  void logd(String logInfo){
		MuLog.logd(logInfo);
	}

}
