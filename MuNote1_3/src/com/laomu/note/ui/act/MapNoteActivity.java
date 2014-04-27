/**
 * 
 */
package com.laomu.note.ui.act;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.AMap.InfoWindowAdapter;
import com.amap.api.maps2d.AMap.OnMapLoadedListener;
import com.amap.api.maps2d.AMap.OnMarkerClickListener;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.UiSettings;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.services.route.RouteSearch;
import com.laomu.note.R;
import com.laomu.note.ui.NoteMainFragment;
import com.laomu.note.ui.base.NoteBaseActivity;
import com.laomu.note.ui.fragment.MapNoteFragment;

/**
 * @author luoyuan.myp
 * 
 *         2014-4-27
 */

public class MapNoteActivity extends NoteBaseActivity {

	private FragmentTransaction frameManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);

		initViews();
	}

	private void initViews() {
		frameManager = getSupportFragmentManager().beginTransaction();
		// 添加主视图
		frameManager.replace(R.id.fl_container, new MapNoteFragment(), "map");
		// 提交事务
		frameManager.commit();

	}

}
