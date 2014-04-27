/**
 * 
 */
package com.laomu.note.common.http;

import java.util.HashMap;
import java.util.Map;

import android.os.Handler;

import com.laomu.note.common.http.imp.HttpMethod;

/**
 * @author luoyuan.myp
 *
 * 2014-4-4
 */
public class HttpReqBean {
	/**http的请求方式，默认为 get*/
	private HttpMethod mMethod = HttpMethod.get;
	/**http的请求url*/
	private String mUrl = null;
	/**http的参数，以map方式保存*/
	private Map<String,String> mParams = new HashMap<String,String>();
	/**http 请求 ，回调UI的handler */
	private Handler mHandler = null;
	/**http 请求 ，response 中匹配返回类型的key */
	private Object mResponse = null;
	
	public HttpReqBean(HttpMethod method,String url,Map params,Handler uiHandler){
		mMethod = method;
		mUrl = url;
		mParams = params;
		mHandler = uiHandler;
	}
	
	public HttpMethod getMethod() {
		return mMethod;
	}

	public void setMethod(HttpMethod mMethod) {
		this.mMethod = mMethod;
	}

	public String getUrl() {
		return mUrl;
	}

	public void setUrl(String mUrl) {
		this.mUrl = mUrl;
	}

	public Map<String, String> getParams() {
		return mParams;
	}

	public void setParams(Map<String, String> mParams) {
		this.mParams = mParams;
	}

	public Handler getHandler() {
		return mHandler;
	}

	public void setHandler(Handler mHandler) {
		this.mHandler = mHandler;
	}

	public Object getResponse() {
		return mResponse;
	}

	public void setResponse(Object mResponse) {
		this.mResponse = mResponse;
	}
	
	
}
