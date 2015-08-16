/**
 * 
 */
package com.laomu.note.ui.base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.laomu.note.R;
import com.laomu.note.common.CommonDefine;
import com.laomu.note.common.MuLog;
import com.laomu.note.ui.NoteApplication;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.PushAgent;
import com.umeng.socialize.controller.RequestType;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;

/**
 * @author luoyuan.myp
 *
 * 2014-2-17
 */
public class NoteBaseActivity extends ActionBarActivity{

	private TextView mCommonTitle;
	private ImageView mCommonLeftImageView;
	private ImageView mCommonRightImageView;
	protected final UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share",
            RequestType.SOCIAL);
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initBaseView();
		//初始化广告
		initAdManeger();
		
		PushAgent.getInstance(this).onAppStart();
	}

	private void initAdManeger() {
		//youmi
//		AdManager.getInstance(this).init(CommonDefine.YOUMI_ID, CommonDefine.YOUMI_SECRET, false);
//		OffersManager.getInstance(this).onAppLaunch();
	}


	protected void initToolbar(String toolbarName) {
		Toolbar toolbar = (Toolbar) findViewById(R.id.app_toolbar);

		// Title
		toolbar.setTitle(toolbarName);
		// Sub Title
//		toolbar.setSubtitle("长按图标试试~");

		setSupportActionBar(toolbar);

//		toolbar.setNavigationIcon(R.drawable.icon_menu_voice);

//		返回键是否显示出来
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		// Menu item click 的監聽事件一樣要設定在 setSupportActionBar 才有作用
//        toolbar.setOnMenuItemClickListener(onMenuItemClick);

		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}



	private void initBaseView() {
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//强制为横屏
		initHeadView();
	}

	private void initHeadView() {
		mCommonTitle = (TextView) findViewById(R.id.tv_common_head_title);
		mCommonLeftImageView = (ImageView) findViewById(R.id.iv_common_head_left);
		mCommonRightImageView = (ImageView) findViewById(R.id.iv_common_head_right);
	}

	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}
	
	protected void setTitle(int leftId,int midId,int rightId){
		if(mCommonLeftImageView == null ||mCommonTitle == null ||mCommonRightImageView == null){
			initHeadView();
		}
		if(mCommonTitle != null){
			mCommonTitle.setText(midId);
		}
		if(mCommonLeftImageView != null){
			mCommonLeftImageView.setBackgroundResource(leftId);
		}
		if(mCommonRightImageView != null){
			mCommonRightImageView.setBackgroundResource(rightId);
		}
	}
	
	protected void setTitle(String midTitle){
		if(mCommonTitle == null){
			mCommonTitle = (TextView) findViewById(R.id.tv_common_head_title);
		}
		if(mCommonTitle != null){
			mCommonTitle.setText(midTitle);
		}
	}
	
	public void toast(String toastInfo){
		Toast.makeText(NoteApplication.appContext, toastInfo, Toast.LENGTH_SHORT).show();
	}
	
	public void logd(String logInfo){
		MuLog.logd(logInfo);
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	
//		try {
//			OffersManager.getInstance(this).onAppExit();
//		} catch (Exception e) {
//		}
	}
}
