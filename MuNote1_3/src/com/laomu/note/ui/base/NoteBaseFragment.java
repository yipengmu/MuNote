/**
 * 
 */
package com.laomu.note.ui.base;

import android.app.AlertDialog;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.laomu.note.R;
import com.laomu.note.common.MuLog;
import com.laomu.note.ui.NoteApplication;

/**
 * @author luoyuan.myp
 * 
 *         2014-2-12
 */
public class NoteBaseFragment extends Fragment {

	private FragmentTransaction mFragmentManeger;
	protected TextView mCommonTitle;
	protected ImageView mCommonLeftImageView;
	protected ImageView mCommonRightImageView;
	protected Toolbar mToolbar;
	protected View mBaseView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return super.onCreateView(inflater, container, savedInstanceState);

	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		mBaseView = view;
		super.onViewCreated(mBaseView, savedInstanceState);
	}

	public void openPage(String fragmentName, Fragment fragment) {
		if (mFragmentManeger == null) {
			mFragmentManeger = getFragmentManager().beginTransaction();
		}
		mFragmentManeger.add(R.id.fl_container, fragment, fragmentName).commit();
		mFragmentManeger.addToBackStack(fragmentName);
	}

	/** note 对话框，传入 string数组 */
	public void showAlertDialog(String[] itemList, OnClickListener listener) {
		new AlertDialog.Builder(getActivity()).setItems(itemList, listener).show();
	}

	protected void setTitle(View v, int leftId, int midId, int rightId) {
		initHeadViewByView(v);
		if (mCommonTitle != null) {
			mCommonTitle.setText(midId);
		}
		if (mCommonLeftImageView != null) {
			mCommonLeftImageView.setBackgroundResource(leftId);
		}
		if (mCommonRightImageView != null) {
			mCommonRightImageView.setBackgroundResource(rightId);
		}
	}

	protected void setTitle(String toolbarName) {
		if(mToolbar == null ){
			if(mBaseView != null) {
				mToolbar = (Toolbar) mBaseView.findViewById(R.id.app_toolbar);
				mToolbar.setTitle(toolbarName);
			}
		}
	}

	private void initHeadViewByView(View v) {
		mCommonTitle = (TextView) v.findViewById(R.id.tv_common_head_title);
		mCommonLeftImageView = (ImageView) v.findViewById(R.id.iv_common_head_left);
		mCommonRightImageView = (ImageView) v.findViewById(R.id.iv_common_head_right);
		mToolbar = (Toolbar) v.findViewById(R.id.app_toolbar);
	}

	protected void setTitle(View v, String midTitle) {
		if (mCommonTitle == null) {
			mCommonTitle = (TextView) v.findViewById(R.id.tv_common_head_title);
		}
		if (mCommonTitle != null) {
			mCommonTitle.setText(midTitle);
		}
	}

	public void toast(String toastInfo) {
		Toast.makeText(NoteApplication.appContext, toastInfo, Toast.LENGTH_SHORT).show();
	}

	public void logd(String logInfo) {
		MuLog.logd(logInfo);
	}
}
