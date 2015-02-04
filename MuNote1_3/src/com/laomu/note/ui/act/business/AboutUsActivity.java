/**
 * 
 */
package com.laomu.note.ui.act.business;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.laomu.note.R;
import com.laomu.note.ui.NoteApplication;
import com.laomu.note.ui.base.NoteBaseActivity;
import com.laomu.note.ui.util.Utils;
import com.laomu.note.ui.webview.WebviewActivity;
import com.squareup.picasso.Picasso;
import com.umeng.message.PushAgent;

/**
 * @author luoyuan.myp
 * 
 *         2014-5-24
 */
public class AboutUsActivity extends NoteBaseActivity implements OnClickListener {

	private TextView mTvHappyTime;
	private String mTitle = "关于我们";
	private Button mTvCurrentVersion;
	private Button mBtnAppUpdate;
	private PushAgent mPushAgent;
	private ImageView bg_logo;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_us_layout);

		initViews();
	}

	private void initViews() {
		findViews();
		setTitle(mTitle);
		mTvCurrentVersion.setText("v " + Utils.getVersionName(this));
	}

	private void findViews() {
		mTvHappyTime = (TextView) findViewById(R.id.tv_happy_time);
		mTvCurrentVersion = (Button) findViewById(R.id.tv_app_version);
		mBtnAppUpdate = (Button) findViewById(R.id.btn_check_app_update);
		bg_logo = (ImageView) findViewById(R.id.iv_us_logo);
		
		Picasso.with(this).load(NoteApplication.cvBean.info_pic).into(bg_logo);
		
		mBtnAppUpdate.setOnClickListener(this);
		mTvHappyTime.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_happy_time:
			gotoHappyTime();
			break;

		case R.id.btn_check_app_update:
			checkAppUpdate();
			break;
			
		default:
			break;
		}
	}

	private void checkAppUpdate() {
		Utils.checkUpdate(this);

		mPushAgent = PushAgent.getInstance(this);
		mPushAgent.onAppStart();
		mPushAgent.enable();
		 final Handler handler = new Handler();
		final Runnable update = new Runnable() {
			@Override
			public void run() {
				updateStatus();
				handler.postDelayed(this, 300);
			}
		};
		handler.postDelayed(update, 300);
		
		String tocken = Utils.getUmengPushTocken(this);
		toast(tocken);
		
	}
	private void updateStatus() {
//		String info = String.format("enabled:%s  isRegistered:%s \n DeviceToken:%s",
//				mPushAgent.isEnabled(), mPushAgent.isRegistered(),
//				mPushAgent.getRegistrationId());
//		tvStatus.setText(info);
//		btnEnable.setChecked(mPushAgent.isEnabled());
	}

	private void gotoHappyTime() {
		Intent intent =new Intent(this,WebviewActivity.class);
		intent.putExtra("url", "file:///android_asset/happy_time.html");
		intent.putExtra("title", "快乐时光");
		startActivity(intent);
	}
}
