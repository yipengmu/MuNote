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

import com.laomu.note.R;
import com.umeng.socialize.controller.RequestType;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;

/**
 * @author luoyuan.myp
 *
 * 2014-2-12
 */
public class NoteBaseFragment extends Fragment{

	private FragmentTransaction mFragmentManeger;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return super.onCreateView(inflater, container, savedInstanceState);
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
}
