/**
 * 
 */
package com.laomu.note.ui.base;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.laomu.note.R;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.controller.RequestType;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;

/**
 * @author luoyuan.myp
 *
 * 2014-2-17
 */
public class NoteBaseActivity extends Activity{

	private TextView mCommonTitle;
	private ImageView mCommonLeftImageView;
	private ImageView mCommonRightImageView;
	
	protected final UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share",
            RequestType.SOCIAL);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		initView();
	}
	
	private void initView() {
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
		if(mCommonTitle != null){
			mCommonTitle.setText(midTitle);
		}
	}
}
