/**
 * 
 */
package com.laomu.note.ui.menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.laomu.note.R;
import com.laomu.note.ui.base.NoteBaseFragment;

/**
 * @author luoyuan.myp
 *
 * 2014-4-7
 */
public class RightSlidingMenu extends NoteBaseFragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.right_menu_frame, container, false);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
	}
}
