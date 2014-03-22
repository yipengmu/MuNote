/**
 * 
 */
package com.laomu.note.ui.share;

import android.app.Activity;
import android.text.TextUtils;

import com.umeng.socialize.common.SocializeConstants;
import com.umeng.socialize.controller.RequestType;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.UMImage;

/**
 * @author luoyuan.myp
 * 
 *         2014-2-20
 */
public class ShareManeger {
	protected UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.login",
			RequestType.SOCIAL);
	private String bitMapUrl = "http://www.umeng.com/images/pic/banner_module_social.png";
	private String shareContent = "便利贴";
	private static ShareManeger instance = null ;
	
	public static ShareManeger instance(){
		if(instance == null){
			instance = new ShareManeger();
		}
		return instance;
	}
	
	public void shareInfo(Activity act) {
		mController.setShareMedia(new UMImage(act, bitMapUrl));
		mController.setShareContent(shareContent);
		mController.openShare(act, false);
	}

	public void setBitmapUrl(String bUrl) {
		if(!TextUtils.isEmpty(bUrl)){
			bitMapUrl = bUrl;
		}
	}

	public void setShareContent(String sContent) {
		if(!TextUtils.isEmpty(sContent)){
			shareContent = sContent;
		}
	}
	
	public void openUserCenter(Activity act) {
		mController.openUserCenter(act,SocializeConstants.FLAG_USER_CENTER_HIDE_LOGININFO);
	}
}
