/**
 * 
 */
package com.laomu.note.ui.base;

import android.app.AlertDialog;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.laomu.note.R;

/**
 * @author luoyuan.myp
 *
 * 2014-2-12
 */
public class NoteBaseFragment extends Fragment{

	private FragmentTransaction mFragmentManeger;
	private TextView mCommonTitle;
	private ImageView mCommonLeftImageView;
	private ImageView mCommonRightImageView;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		initView();
	}
	
	private void initView() {
		mCommonTitle = (TextView) getActivity().findViewById(R.id.tv_common_head_title);
		mCommonLeftImageView = (ImageView) getActivity().findViewById(R.id.iv_common_head_left);
		mCommonRightImageView = (ImageView) getActivity().findViewById(R.id.iv_common_head_right);
	}

	public void openPage(String fragmentName,Fragment fragment){
		if(mFragmentManeger == null){
			 mFragmentManeger = getFragmentManager().beginTransaction();
		}
		mFragmentManeger.add(R.id.fl_container, fragment, fragmentName).commit();
	}

	/**note 对话框，传入 string数组*/
	public void showAlertDialog(String[] itemList,OnClickListener listener){
		new AlertDialog.Builder(getActivity())
		 .setItems(itemList, listener)
		 	.show();
	}
	
	protected void setTitle(int leftId,int midId,int rightId){
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
