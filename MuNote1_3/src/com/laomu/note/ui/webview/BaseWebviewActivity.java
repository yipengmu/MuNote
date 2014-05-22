/**
 * 
 */
package com.laomu.note.ui.webview;

import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.laomu.note.R;
import com.laomu.note.ui.base.NoteBaseActivity;

/**
 * @author luoyuan.myp
 *
 * 2014-4-16
 */
public class BaseWebviewActivity extends NoteBaseActivity{

	protected WebView mWebview;
	protected BaseWebViewClient mBaseWebviewClient;
	protected BaseWebChromeClient mBaseWebChromeClient;
	protected String mCurrentUrl = "http://www.taobao.com";
	protected String mGeneralUrl;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.general_webview_layout);
		findviews();
		initWebviewSettings();
		loadUrl(mCurrentUrl);
	}
	
	private void initWebviewSettings() {
		mWebview.setWebChromeClient(new BaseWebChromeClient());
		mWebview.setWebViewClient(new BaseWebViewClient());
		
		WebSettings settings = mWebview.getSettings();
		settings.setJavaScriptEnabled(true);
		
	}
	
	
	private void findviews() {
		mWebview = (WebView) findViewById(R.id.wv_general);
	}
	
	
	protected void loadUrl(String url){
		mWebview.loadUrl(url);
	}
}
