/**
 * 
 */
package com.laomu.note.ui.menu;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.laomu.note.R;
import com.laomu.note.ui.act.CitySelectionActivity;
import com.laomu.note.ui.act.MapNoteActivity;
import com.laomu.note.ui.base.NoteBaseFragment;
import com.laomu.note.ui.webview.BaseWebviewActivity;

/**
 * @author luoyuan.myp
 * 
 *         2014-4-7
 */
public class LeftSlidingMenu extends NoteBaseFragment implements OnClickListener {
	private TextView tv_personal_clock;
	private TextView tv_map_mark;
	private TextView tv_info;

	private int FLAG_FOR_CITY_LIST = 1;
	private int FLAG_FOR_MAP_VIEW = 2;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.left_menu_frame, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		initViews(view);
	}

	private void initViews(View view) {
		findviews(view);
		initUI();
	}

	private void initUI() {

	}

	private void findviews(View view) {
		tv_personal_clock = (TextView) view.findViewById(R.id.tv_personal_clock);
		tv_map_mark = (TextView) view.findViewById(R.id.tv_map_mark);
		tv_info = (TextView) view.findViewById(R.id.tv_info);

		tv_map_mark.setOnClickListener(this);
		tv_info.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_info:
			intoCityList();
			break;
		case R.id.tv_map_mark:
			intoMapView();
			break;

		default:
			break;
		}
	}

	private void intoCityList() {
		Intent intent = new Intent(getActivity(), CitySelectionActivity.class);
		startActivityForResult(intent, FLAG_FOR_CITY_LIST);
	}

	private void intoMapView() {
		Intent intent = new Intent(getActivity(), MapNoteActivity.class);
		startActivityForResult(intent, FLAG_FOR_MAP_VIEW);
	}

}
