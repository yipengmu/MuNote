package com.laomu.note.ui.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Handler;
import android.text.TextUtils;
import android.webkit.URLUtil;
import android.webkit.WebResourceResponse;
import android.widget.Toast;

import com.google.gson.Gson;
import com.laomu.note.R;
import com.laomu.note.common.MuLog;
import com.laomu.note.common.lbs.LocationInfoManeger;
import com.laomu.note.common.preferences.PreferenceCenter;
import com.laomu.note.common.weather.WeatherController;
import com.laomu.note.data.database.OrmDbManeger;
import com.laomu.note.data.model.LocationBean;
import com.laomu.note.ui.NoteApplication;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;

/**
 * @author luoyuan.myp
 * 
 *         2014-2-17
 */
public class Utils {

	public static String getTimeInfo(Date date) {
		if (date == null) {
			return "时间统计出错 --:--";
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);

		// Calendar now = Calendar.getInstance();
		// c.setTimeInMillis(SystemClock.currentThreadTimeMillis());
		StringBuilder sb = new StringBuilder();
		// if(isSameYear(c,now)){
		//
		// }
		sb.append(c.get(Calendar.YEAR) + "年" + (c.get(Calendar.MONTH) + 1) + "月"
				+ c.get(Calendar.DAY_OF_MONTH) + "日");
		sb.append(" ");
		sb.append(c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE));
		return sb.toString();
	}

	private static boolean isSameYear(Calendar c, Calendar now) {
		if (c.get(Calendar.YEAR) == now.get(Calendar.YEAR)) {
			return true;
		}
		return false;
	}

	public static boolean isNetReady() {
		ConnectivityManager connMgr = (ConnectivityManager) NoteApplication.appContext
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
			// fetch data
			return true;
		} else {
			return false;
		}
	}

	public static void toast(String toastInfo) {
		Toast.makeText(NoteApplication.appContext, toastInfo, Toast.LENGTH_SHORT).show();
	}

	public static void logd(String logInfo) {
		MuLog.logd(logInfo);
	}

	public static WebResourceResponse getLocalWebResource(String url) {
		if (Utils.isUrlCached(url)) {

			// WebResourceResponse m = new
			// WebResourceResponse(Utils.getMimeType(url), encoding, )
		}
		return null;
	}

	private static boolean isUrlCached(String url) {
		HashMap<String, String> urlMap = new HashMap<String, String>();
		String urlJsonMap = PreferenceCenter.getPreferences().getUrlLocalCacheMap();
		try {
			JSONArray jArray = new JSONArray(urlJsonMap);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static String getMimeType(String fileUrl) throws java.io.IOException,
			MalformedURLException {
		String type = null;
		URL u = new URL(fileUrl);
		URLConnection uc = null;
		uc = u.openConnection();
		type = uc.getContentType();
		return type;
	}

	public static int getLocationIdFromDB(LocationBean mLocation) {
		return OrmDbManeger.getInstance().addLocation(mLocation);
	}

	public static Bitmap getBitmapFromUrl(String urlSrc) {
		try {
			if (TextUtils.isEmpty(urlSrc)) {
				return null;
			}
			URL url = new URL(urlSrc);
			if (url.getProtocol().equals("file")) { // 本地文件
				String file_path = url.getPath();
				if (TextUtils.isEmpty(file_path)) {
					return null;
				}
				File file = new File(file_path);
				FileInputStream io = new FileInputStream(file);
				Bitmap myBitmap = BitmapFactory.decodeStream(io);
				io.close();
				return myBitmap;
			} else {
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				connection.setDoInput(true);
				connection.connect();
				InputStream input = connection.getInputStream();
				Bitmap myBitmap = BitmapFactory.decodeStream(input);
				return myBitmap;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String getWeatherUrl(String city) {
		return WeatherController.getWeatherUrl(city);
	}

	public static int computeInitialSampleSize(BitmapFactory.Options options, int minSideLength,
			int maxNumOfPixels) {
		double w = options.outWidth;
		double h = options.outHeight;
		int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math.sqrt(w * h
				/ maxNumOfPixels));
		int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(
				Math.floor(w / minSideLength),

				Math.floor(h / minSideLength));
		if (upperBound < lowerBound) {
			// return the larger one when there is no overlapping zone.
			return lowerBound;
		}
		if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
			return 1;
		} else if (minSideLength == -1) {
			return lowerBound;
		} else {
			return upperBound;
		}
	}

	public static String getFileTimeStamp(String filePath) {
		File file = new File(filePath);
		long timeL = 0;
		if (file != null) {
			timeL = file.lastModified();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return formatter.format(timeL);
		}
		return "时间：--";
	}

	public static String getFileName(String filePath) {
		File file = new File(filePath);
		if (file.isFile()) {
			return file.getName();
		}
		return filePath;
	}

	public static LocationBean getLocation() {
		return LocationInfoManeger.getInstance().getmLocationInfoModel();
	}

	public static int prepareCameraKacha(Context c) {
		final SoundPool soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 100);
		return soundPool.load(c, R.raw.kacha, 1);
	}

	public static void startCameraKacha(Context c,final int id) {
		final SoundPool soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 100);
		AudioManager mgr = (AudioManager) c.getSystemService(Context.AUDIO_SERVICE);
		float streamVolumeCurrent = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);
		float streamVolumeMax = mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		final float volume = streamVolumeCurrent / streamVolumeMax;
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				soundPool.play(id, volume, volume, 1, 0, 1f);
			}
		}, 20);
	}

	public static String getVersionName(Context c) {
		 String version = "v--";
		 // 获取packagemanager的实例
        PackageManager packageManager = c.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = null;
		try {
			packInfo = packageManager.getPackageInfo("com.laomu.note",0);
			version = packInfo.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
        return version;
	}

	public static void checkUpdate(final Context c) {
		UmengUpdateAgent.setUpdateOnlyWifi(true);
        UmengUpdateAgent.setUpdateAutoPopup(false);
        UmengUpdateAgent.setUpdateListener(new UmengUpdateListener() {

            @Override
            public void onUpdateReturned(int updateStatus,
                    UpdateResponse updateInfo) {
                if (updateStatus == 0 && updateInfo != null) {
                    showUpdateDialog(c,updateInfo.path, updateInfo.updateLog);
                }
                // case 0: // has update
                // case 1: // has no update
                // case 2: // none wifi
                // case 3: // time out
            }

        });

        UmengUpdateAgent.update(c);		
	}
	
	 public static void showUpdateDialog(final Context c,final String downloadUrl, final String message) {
	        AlertDialog.Builder updateAlertDialog = new AlertDialog.Builder(c);
	        updateAlertDialog.setIcon(R.drawable.ic_launcher);
	        updateAlertDialog.setTitle(R.string.app_name);
	        updateAlertDialog.setMessage("有新版本了");
	        updateAlertDialog.setNegativeButton("ok",
	                new DialogInterface.OnClickListener() {
	                    @Override
	                    public void onClick(DialogInterface dialog, int which) {
	                        dialog.dismiss();
	                        try {
	                            c.startActivity(new Intent(Intent.ACTION_VIEW, Uri
	                                    .parse(downloadUrl)));
	                        } catch (Exception ex) {

	                        }
	                    }
	                }).setPositiveButton("no", null);
	            updateAlertDialog.show();
	    }

}
