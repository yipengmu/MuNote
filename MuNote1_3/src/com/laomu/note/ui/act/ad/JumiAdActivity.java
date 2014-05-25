/**
 * 
 */
package com.laomu.note.ui.act.ad;

import net.youmi.android.offers.OffersManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.j.p.api.PopManager;
import com.laomu.note.R;
import com.laomu.note.ui.base.NoteBaseActivity;

/**
 * @author luoyuan.myp
 * 
 *         2014-5-24
 */
public class JumiAdActivity extends NoteBaseActivity {

	/** 聚米 ad maneger */
	PopManager mJuMiManager;
	private String mTitle = "咦,下载有奖!";
	private TextView mTvDownloadInfo;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.ad_main_layout);
		initViews();
		setTitle(mTitle);

		startADs();
//		new Handler().postDelayed(new Runnable() {
//			
//			@Override
//			public void run() {
//				startADs();
//			}
//		}, 1500);
	}

	private void startADs() {

		// startYoumiAd();
		startJumiAd();
		// closeJumiAd();
	}

	private void initViews() {
		mTvDownloadInfo = (TextView) findViewById(R.id.tv_download_info);
	}

	private void startYoumiAd() {
		// 关闭有米log
		// AdManager.getInstance(this).setEnableDebugLog(false);

		OffersManager.getInstance(this).showOffersWall();
		// OffersManager.getInstance(this).showOffersWallDialog(this);
	}

	private void closeJumiAd() {
		mJuMiManager.q();
	}

	private void startJumiAd() {

		mJuMiManager = PopManager.getInstance(getApplicationContext(),
				"9906a7e0-1578-4f06-8fb7-23bfe710a3bb", 1);
		// 配置插屏，开启外插屏
		mJuMiManager.c(getApplicationContext(), 1, 3, true, true);
		// 配置外插屏
		mJuMiManager.o(getApplicationContext(), false, 4, true, true, true);
		mJuMiManager.s(this);
	}
}
