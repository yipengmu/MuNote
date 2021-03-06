/**
 * 
 */
package com.laomu.note.ui.menu;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.laomu.note.R;
import com.laomu.note.ui.act.ad.JumiAdActivity;
import com.laomu.note.ui.act.business.AboutUsActivity;
import com.laomu.note.ui.base.NoteBaseFragment;
import com.laomu.note.ui.webview.WebviewActivity;
import com.umeng.fb.FeedbackAgent;

/**
 * @author luoyuan.myp
 * 
 *         2014-4-7
 */
public class RightSlidingMenu extends NoteBaseFragment implements OnClickListener {
	private TextView mTvFeedBack;
	private TextView mTvAboutUs;
	private View mView;
	private TextView mTvAdWall;
	private TextView mTvHistoryVersion;
	
	public static String RIGHT_MENU_TAG = "RightSlidingMenu";
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return mView = inflater.inflate(R.layout.right_menu_frame, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		findviews();
	}

	private void findviews() {
		mTvAdWall = (TextView) mView.findViewById(R.id.tv_ad_wall);
		mTvFeedBack = (TextView) mView.findViewById(R.id.tv_feedback);
		mTvAboutUs = (TextView) mView.findViewById(R.id.tv_about_us);
		mTvHistoryVersion = (TextView) mView.findViewById(R.id.tv_version_history);
		
		mTvHistoryVersion.setOnClickListener(this);
		mTvAdWall.setOnClickListener(this);
		mTvFeedBack.setOnClickListener(this);
		mTvAboutUs.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_ad_wall:
			gotoAdWall();
			break;
		case R.id.tv_feedback:
			gotoFeedBack();
			break;
		case R.id.tv_about_us:
			gotoAboutUs();
			break;

		case R.id.tv_version_history:
			openAppHistoryVersion();
			break;
			
		default:
			break;
		}
	}

	private void openAppHistoryVersion() {
		Intent intent = new Intent(getActivity(),WebviewActivity.class);

		intent.putExtra("url", "file:///android_asset/history.html");
		intent.putExtra("title", "历史版本");

		startActivity(intent);
	}

	private void gotoAboutUs() {
		Intent intent = new Intent(getActivity(),AboutUsActivity.class);
		startActivity(intent);
	}

	private void gotoFeedBack() {
		FeedbackAgent agent = new FeedbackAgent(getActivity());
		agent.startFeedbackActivity();
	}

	private void gotoAdWall() {
		Intent intent = new Intent(getActivity(), JumiAdActivity.class);
		startActivity(intent);
	}
}
