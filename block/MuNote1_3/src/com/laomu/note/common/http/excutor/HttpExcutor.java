/**
 * 
 */
package com.laomu.note.common.http.excutor;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.laomu.note.common.MuLog;
import com.laomu.note.common.http.BaseHttp;
import com.laomu.note.common.http.HttpMapDefine;
import com.laomu.note.common.http.HttpReqBean;
import com.laomu.note.common.http.imp.HttpInterface;
import com.laomu.note.common.http.response.BaiduWeatherResult;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.zip.GZIPInputStream;

/**
 * @author luoyuan.myp
 * 
 */
public class HttpExcutor extends BaseHttp implements HttpInterface {

	private String mUrl;
	private InputStream is;
	private Handler mHttpInfoHandler;
	private String mapKey = null;
	private HttpReqBean req;

	public void setUrl(String url) {
		this.mUrl = url;
	}

	public void setMapKey(String mapKey) {
		this.mapKey = mapKey;
	}

	@Override
	public void req(String urlKey, HttpReqBean req) {
		if (TextUtils.isEmpty(req.getUrl())) {
			MuLog.logd("url is empty");
			return;
		} else {
			this.req = req;
			setMapKey(urlKey);
			setUrl(req.getUrl());
		}
		mHttpInfoHandler = req.getHandler();
		workThread.start();
	}

	Thread workThread = new Thread(new Runnable() {

		@Override
		public void run() {

			try {
				// 创建一个URL对象
				URL mURL = new URL(mUrl);
				// 利用HttpURLConnection对象从网络中获取网页数据
				HttpURLConnection conn = (HttpURLConnection) mURL.openConnection();
				// 设置连接超时
				conn.setConnectTimeout(6 * 1000);
				// 对响应码进行判断
				if (conn.getResponseCode() != 200) // 从Internet获取网页,发送请求,将网页以流的形式读回来
					throw new RuntimeException("请求url失败");
				// 得到网络返回的输入流
				is = conn.getInputStream();

				GZIPInputStream gzipIs = null;
				DataInputStream dis = null;

				if (conn.getContentEncoding() != null
						&& "gzip".compareTo(conn.getContentEncoding()) == 0) {
					gzipIs = new GZIPInputStream(is);
					dis = new DataInputStream(gzipIs);
				} else {
					dis = new DataInputStream(is);
				}

				BufferedReader reader = new BufferedReader(new InputStreamReader(dis, "UTF-8"), 128);

				handleOutput(mapKey, reader);

			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	});

	protected Bundle getResponse(String mapkey, Object res) {
		Bundle bundle = new Bundle();
		if (res != null) {
			if (res instanceof String) {
				bundle.putParcelable("res", parseJson2ResponceObj(mapkey,(String)res));
			}else if(res instanceof Bitmap){
				bundle.putParcelable("res", (Bitmap)res);
			}
		}
		return bundle;
	}

	protected void handleOutput(String mapKey, BufferedReader reader) throws IOException {
		Object res = null;
		if (HttpMapDefine.Bitmap.equals(mapKey)) {
			res = handleBitmapResponse(reader, req.getUrl());
		} else {
			//默认为 HttpMapDefine.
			res = handleStringResponse(reader);
		}

		Message message = new Message();
		message.setData(getResponse(mapKey, res));
		mHttpInfoHandler.sendMessage(message);
		if(res != null){
			MuLog.logd(res.getClass() + ":"+res.toString());
		}
	}

	private Bitmap handleBitmapResponse(BufferedReader reader, String urlSrc) {
		if (TextUtils.isEmpty(urlSrc)) {
			return null;
		}
		URL url = null;
		Bitmap myBitmap = null;
		try {
			url = new URL(urlSrc);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoInput(true);
			connection.connect();
			InputStream input = connection.getInputStream();
			myBitmap = BitmapFactory.decodeStream(input);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return myBitmap;
	}

	private String handleStringResponse(BufferedReader reader) throws IOException {
		StringBuffer mStringBuf = new StringBuffer();
		mStringBuf.setLength(0);
		char[] mReadBuf = new char[1024];
		int len;
		while ((len = reader.read(mReadBuf)) != -1) {
			mStringBuf.append(mReadBuf, 0, len);
		}
		reader.close();
		return mStringBuf.toString();
	}

	private Parcelable parseJson2ResponceObj(String mapkey, String jsonRes) {
		if(TextUtils.isEmpty(mapkey)){
			return null;
		}
		
		Parcelable pResult = null;
		if(HttpMapDefine.Weather.equals(mapkey)){
			Gson gson = new Gson();
			try {
				pResult = gson.fromJson(jsonRes, BaiduWeatherResult.class);
			} catch (Exception e) {
			}
		}
		return pResult;
	}
}
