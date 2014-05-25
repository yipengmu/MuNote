/**
 * 
 */
package com.laomu.note.ui.base;

import net.youmi.android.AdManager;
import net.youmi.android.offers.OffersManager;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.laomu.note.R;
import com.laomu.note.common.CommonDefine;
import com.laomu.note.common.MuLog;
import com.laomu.note.ui.NoteApplication;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.controller.RequestType;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;

/**
 * @author luoyuan.myp
 *
 * 2014-2-17
 */
public class NoteBaseActivity extends FragmentActivity{

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
	}

	private void initAdManeger() {
		//youmi
		AdManager.getInstance(this).init(CommonDefine.YOUMI_ID, CommonDefine.YOUMI_SECRET, false);
		OffersManager.getInstance(this).onAppLaunch();
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
	
		try {
			OffersManager.getInstance(this).onAppExit();
		} catch (Exception e) {
		}
	}
}
