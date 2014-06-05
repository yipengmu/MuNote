/**
 * 
 */
package com.laomu.note.ui.share;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.laomu.note.R;
import com.laomu.note.common.CommonDefine;
import com.laomu.note.ui.NoteApplication;
import com.umeng.socialize.common.SocializeConstants;
import com.umeng.socialize.controller.RequestType;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.UMWXHandler;

/**
 * @author luoyuan.myp
 * 
 *         2014-2-20
 */
public class ShareManeger {
	protected static UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.login",
			RequestType.SOCIAL);
	private String bitMapUrl = "http://www.eoemarket.com/soft/154351.html";
	private String shareContent = "便利贴";
	private String mTitle = "便利贴";
	private UMWXHandler wxHandler;
	// 微信图文分享必须设置一个url
	private String contentUrl = "http://www.eoemarket.com/soft/154351.html";
	private UMWXHandler circleHandler;
	private static ShareManeger instance = null;
	private static Activity mActivity;
		
	public static ShareManeger instance(Activity act) {
		mActivity = act;
		if (instance == null) {
			instance = new ShareManeger();
		}
		return instance;
	}

	/**
	 * 
	 */
	public ShareManeger() {
		initControllerManeger();
		initWeixinSNS();
	}

	private void initControllerManeger() {
		//openqq
		mController.getConfig().setSsoHandler( new QZoneSsoHandler(mActivity, CommonDefine.OpenQQ_AppID,  CommonDefine.OpenQQ_AppKey) ); 		
		//opensinaweibo
		mController.getConfig().setSinaSsoHandler(new SinaSsoHandler());
	
		mController.getConfig().setSsoHandler(new SinaSsoHandler());
	}

	public static UMSocialService getController (){
		return mController;
	}
	
	private void initWeixinSNS() {
		// 添加微信平台，参数1为当前Activity, 参数2为用户申请的AppID, 参数3为点击分享内容跳转到的目标url
		wxHandler = mController.getConfig().supportWXPlatform(NoteApplication.appContext, CommonDefine.Weixin_AppID,
				contentUrl);
		// 设置分享标题
		wxHandler.setWXTitle(mTitle);
		// 支持微信朋友圈
		circleHandler = mController.getConfig().supportWXCirclePlatform(
				NoteApplication.appContext, CommonDefine.Weixin_AppID, contentUrl);
		circleHandler.setIcon(R.drawable.ic_launcher);
		circleHandler.setWXTitle(mTitle);		
	}

	public void shareInfo(Activity act) {
		mController.openShare(act, false);
	}

	public void setBitmapUrl(String bUrl) {
		if (!TextUtils.isEmpty(bUrl)) {
			bitMapUrl = bUrl;
		}
	}

	public void setShareContent(String sContent) {
		if (!TextUtils.isEmpty(sContent)) {
			mController.setShareContent(sContent + " 点击下载便利贴:" + contentUrl);
			circleHandler.setCircleTitle(sContent);
		}
	}

	public void setBitmapResource(Context c, int id) {
		if (id == 0) {
			mController.setShareImage(new UMImage(c, R.drawable.ic_launcher));
		} else {
			mController.setShareImage(new UMImage(c, id));
		}
	}

	public void openUserCenter(Activity act) {
		mController.openUserCenter(act, SocializeConstants.FLAG_USER_CENTER_HIDE_LOGININFO);
	}
}
