/**
 * 
 */
package com.laomu.note.ui.webview;

import com.laomu.note.ui.util.Utils;

import android.graphics.Bitmap;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * @author luoyuan.myp
 *
 * 2014-4-16
 */
public class BaseWebViewClient extends WebViewClient{

	@Override
	public void onPageStarted(WebView view, String url, Bitmap favicon) {
		// TODO Auto-generated method stub
		super.onPageStarted(view, url, favicon);
	}
	
	@Override
	public void onPageFinished(WebView view, String url) {
		// TODO Auto-generated method stub
		super.onPageFinished(view, url);
	}
	
	@Override
	public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
		return Utils.getLocalWebResource(url);
	}
	
	@Override
	public void onLoadResource(WebView view, String url) {
		super.onLoadResource(view, url);
	}
}
