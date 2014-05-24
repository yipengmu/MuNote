/**
 * 
 */
package com.laomu.note.ui.webview;

import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

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
	protected String mTitle;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.general_webview_layout);
		initData();
		initViews();
	}
	
	private void initViews() {
		findviews();
		initWebviewSettings();
		initTitle();
		loadWebviewUrl(mCurrentUrl);
	}

	private void initTitle() {
		setTitle(mTitle);
	}

	private void initData() {
		mGeneralUrl = mCurrentUrl = getIntent().getStringExtra("url");
		mTitle = getIntent().getStringExtra("title");
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
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
	protected void loadWebviewUrl(String url){
		if(!TextUtils.isEmpty(url)){
			mWebview.loadUrl(url);
		}else{
			toast("无效的 url..");
		}
	}
}
