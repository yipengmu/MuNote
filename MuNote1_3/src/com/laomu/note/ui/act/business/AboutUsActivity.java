/**
 * 
 */
package com.laomu.note.ui.act.business;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.laomu.note.R;
import com.laomu.note.ui.base.NoteBaseActivity;
import com.laomu.note.ui.webview.WebviewActivity;

/**
 * @author luoyuan.myp
 * 
 *         2014-5-24
 */
public class AboutUsActivity extends NoteBaseActivity implements OnClickListener {

	private TextView mTvHappyTime;
	private TextView mTvHistoryVersion;
	private String mTitle = "关于我们";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_us_layout);

		initViews();
	}

	private void initViews() {
		findViews();
		setTitle(mTitle);
	}

	private void findViews() {
		mTvHappyTime = (TextView) findViewById(R.id.tv_happy_time);
		mTvHistoryVersion = (TextView) findViewById(R.id.tv_app_history_version);
		mTvHappyTime.setOnClickListener(this);
		mTvHistoryVersion.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_happy_time:
			gotoHappyTime();
			break;
		case R.id.tv_app_history_version:
			gotoAppHistoryVersion();
			break;

		default:
			break;
		}
	}

	private void gotoAppHistoryVersion() {
		
	}

	private void gotoHappyTime() {
		Intent intent =new Intent(this,WebviewActivity.class);
		intent.putExtra("url", "file:///android_asset/happy_time.html");
		intent.putExtra("title", "快乐时光");
		startActivity(intent);
	}
}
