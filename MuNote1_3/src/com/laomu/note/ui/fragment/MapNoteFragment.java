/**
 * 
 */
package com.laomu.note.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.UiSettings;
import com.amap.api.maps2d.AMap.InfoWindowAdapter;
import com.amap.api.maps2d.AMap.OnMapLoadedListener;
import com.amap.api.maps2d.AMap.OnMarkerClickListener;
import com.amap.api.maps2d.model.Marker;
import com.laomu.note.R;
import com.laomu.note.ui.base.NoteBaseFragment;

/**
 * @author luoyuan.myp
 *
 * 2014-4-27
 */
public class MapNoteFragment extends NoteBaseFragment implements OnMarkerClickListener, OnMapLoadedListener, InfoWindowAdapter{

	private View mView;
	private AMap aMap;
	private MapView mapView;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return mView = inflater.inflate(R.layout.map_layout, container, false);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		findViews(savedInstanceState);
		initMapView();
		initViews();
	}

	private void initViews() {
	}

	private void initMapView() {
		if (aMap == null) {
			aMap = mapView.getMap();
			UiSettings uiSettings = aMap.getUiSettings();
			aMap.setOnMarkerClickListener(this);
			aMap.setOnMapLoadedListener(this);
			aMap.setInfoWindowAdapter(this);// 添加显示infowindow监听事件
			uiSettings.setScaleControlsEnabled(true);
			uiSettings.setZoomControlsEnabled(true);
			uiSettings.setCompassEnabled(true);
			uiSettings.setMyLocationButtonEnabled(true);
			uiSettings.setAllGesturesEnabled(true);

			// 没有检测 或者检测结果为false 均不会执行
//			if (mIsSameCity) {
//				showRouteAsyn();
//			} else {
//				// 在进行逆地理编码时，会出现重复Add的情况
//				addMarkersToMap();
//			}
		}		
	}

	private void findViews(Bundle savedInstanceState) {
		mapView = (MapView) mView.findViewById(R.id.map_note_view);		
		mapView.onCreate(savedInstanceState);// 此方法必须重写
	}

	@Override
	public boolean onMarkerClick(Marker arg0) {
		return false;
	}

	@Override
	public void onMapLoaded() {
		
	}

	@Override
	public View getInfoContents(Marker arg0) {
		return null;
	}

	@Override
	public View getInfoWindow(Marker arg0) {
		return null;
	}
}
