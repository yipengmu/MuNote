/**
 * 
 */
package com.laomu.note.common.http.excutor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.text.TextUtils;

import com.laomu.note.common.MuLog;
import com.laomu.note.common.http.BaseHttp;
import com.laomu.note.common.http.HttpReqBean;
import com.laomu.note.common.http.imp.HttpInterface;

/**
 * @author luoyuan.myp
 *
 * 2014-4-3
 */
public class HttpExcutor extends BaseHttp implements HttpInterface{

	private HttpClient mHttpClient = null;
	private String mUrl;
	private InputStream is;
	private Handler mHttpInfoHandler;
	private String mapKey = null;
	
	public void setUrl(String url){
		this.mUrl = url;
	}
	
	public void setMapKey(String mapKey){
		this.mapKey = mapKey;
	}
	
	@Override
	public void req(String urlKey, HttpReqBean req) {
		if(TextUtils.isEmpty(req.getUrl())){
			MuLog.logd("url is empty");
			return;
		}else{
			setMapKey(urlKey);
			setUrl(req.getUrl());
		}
		mHttpInfoHandler = req.getHandler();
		workThread.start();
	}

	Thread workThread = new Thread(new Runnable() {
		
		@Override
		public void run() {
		
			if(mHttpClient == null){
				mHttpClient = new DefaultHttpClient();
			}
			try {
//				1)创建一个URL对象
				URL mURL = new URL(mUrl);

//				2)利用HttpURLConnection对象从网络中获取网页数据
				HttpURLConnection conn = (HttpURLConnection) mURL.openConnection();

//				3)设置连接超时
				conn.setConnectTimeout(6*1000);

//				4)对响应码进行判断
				if (conn.getResponseCode() != 200)    //从Internet获取网页,发送请求,将网页以流的形式读回来
				throw new RuntimeException("请求url失败");

//				5)得到网络返回的输入流
				is = conn.getInputStream();
				
				InputStreamReader isr = new InputStreamReader(is);
				BufferedReader br = new BufferedReader(isr);
				StringBuilder sb = new StringBuilder();
				String line = null;
				while((line = br.readLine()) != null){
					sb.append(line);
				}
				String res = sb.toString();
				Message message = new Message();
				message.setData(getResponse(mapKey,res));
				mHttpInfoHandler.sendMessage(message);
				
				MuLog.logd(res);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	});

	protected Bundle getResponse(String mapkey, String jsonRes) {
		Bundle bundle = new Bundle();
		bundle.putParcelable("res", parseJson2ResponceObj(mapkey,jsonRes));
		return bundle;
	}

	private Parcelable parseJson2ResponceObj(String mapkey2, String jsonRes) {
		
		return null;
	}

}
