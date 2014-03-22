/**
 * 
 */
package com.laomu.note.ui;

import com.laomu.note.R;
import com.laomu.note.ui.base.NoteBaseFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author luoyuan.myp
 * 
 *         2014-2-12
 */
public class TextCreateNoteFragment extends NoteBaseFragment {

	private View mView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return mView = inflater.inflate(R.layout.text_create_note, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
	}
}
