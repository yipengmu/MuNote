/**
 * 
 */
package com.laomu.note.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.AMap.InfoWindowAdapter;
import com.amap.api.maps2d.AMap.OnMapLoadedListener;
import com.amap.api.maps2d.AMap.OnMarkerClickListener;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.UiSettings;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.laomu.note.R;
import com.laomu.note.data.database.OrmDbManeger;
import com.laomu.note.data.model.LocationBean;
import com.laomu.note.data.model.NoteBean;
import com.laomu.note.ui.NoteApplication;
import com.laomu.note.ui.act.TextNoteActivity;
import com.laomu.note.ui.base.NoteBaseFragment;

/**
 * @author luoyuan.myp
 * 
 *         2014-4-27
 */
public class MapNoteFragment extends NoteBaseFragment implements OnClickListener, LocationSource,
		AMapLocationListener, OnMarkerClickListener, InfoWindowAdapter, OnMapLoadedListener {

	private View mView;
	private AMap aMap;
	private MapView mapView;

	private OnLocationChangedListener mListener;
	private LocationManagerProxy aMapManager;
	private String mTitle = "地图足迹";
	private ArrayList<NoteBean> mNoteDBData;
	private List<LocationBean> mNoteLocationData;
	private Marker mPoiMarker;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return mView = inflater.inflate(R.layout.map_layout, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		findViews(savedInstanceState);
		loadNoteData();
		initViews();

	}

	private void initViews() {
		setTitle(mView, mTitle);
		initMapView();
		drawMapViewLayers();
	}

	private void loadNoteData() {
		mNoteDBData = (ArrayList<NoteBean>) OrmDbManeger.getInstance().queryNote();
	}

	private void initMapView() {
		if (aMap == null) {
			aMap = mapView.getMap();
			aMap.setOnMarkerClickListener(this);
			aMap.setOnMapLoadedListener(this);
			aMap.setInfoWindowAdapter(this);// 添加显示infowindow监听事件
			aMap.setMyLocationEnabled(true);// 是否可触发定位并显示定位层
			aMap.setLocationSource(this);// 设置定位监听

			UiSettings uiSettings = aMap.getUiSettings();
			uiSettings.setScaleControlsEnabled(true);
			uiSettings.setZoomControlsEnabled(true);
			uiSettings.setCompassEnabled(true);
			uiSettings.setMyLocationButtonEnabled(true);
			uiSettings.setAllGesturesEnabled(true);
			uiSettings.setCompassEnabled(true);
			uiSettings.setZoomControlsEnabled(true);
			uiSettings.setMyLocationButtonEnabled(true); // 是否显示默认的定位按钮
		}
	}

	private void drawMapViewLayers() {
		if (mNoteDBData == null) {
			return;
		}
		for(NoteBean noteBean:mNoteDBData){
			drawMapItem(noteBean);
		}
	}

	private void drawMapItem(NoteBean noteBean) {
		try {
			mNoteLocationData = OrmDbManeger.getInstance().queryLocation(noteBean.note_location_id);
			LocationBean lobean = mNoteLocationData.get(0);
			mPoiMarker = aMap.addMarker(new MarkerOptions().position(
					new LatLng(Double.valueOf(lobean.latitude), Double.valueOf(lobean.longtitude)))
					.title(noteBean.note_title).snippet(noteBean.note_time).icon(
					BitmapDescriptorFactory.fromResource(R.drawable.icon_location_highlighted)));
			mPoiMarker.setObject(noteBean);
			mPoiMarker.showInfoWindow();
		} catch (Exception e) {
		}
	}

	private void renderInfoWindow(final Marker marker, View infoWindow) {
		TextView marker_name_text = (TextView) infoWindow.findViewById(R.id.marker_name_text);
		TextView marker_sub_bottom_text = (TextView) infoWindow.findViewById(R.id.marker_sub_bottom_text);
		marker_name_text.setText(marker.getTitle());
		marker_sub_bottom_text.setText(marker.getSnippet());
		infoWindow.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				gotoNoteDetailActivity(marker);
			}
		});
	}
	
	protected void gotoNoteDetailActivity(Marker marker) {
		Intent intent = new Intent();
		intent.setClass(getActivity(), TextNoteActivity.class);
		intent.putExtra("note_bean", (NoteBean)marker.getObject());
		startActivity(intent);
	}

	private void findViews(Bundle savedInstanceState) {
		mapView = (MapView) mView.findViewById(R.id.map_note_view);
		mapView.onCreate(savedInstanceState);// 此方法必须重写
	}

	/**
	 * 定位成功后回调函数
	 */
	@Override
	public void onLocationChanged(AMapLocation aLocation) {
		if (mListener != null) {
			mListener.onLocationChanged(aLocation);// 显示系统小蓝点
		}
	}

	/**
	 * 激活定位
	 */
	@Override
	public void activate(OnLocationChangedListener listener) {
		mListener = listener;
		if (aMapManager == null) {
			aMapManager = LocationManagerProxy.getInstance(getActivity());
			/*
			 * mAMapLocManager.setGpsEnable(false);//
			 * 1.0.2版本新增方法，设置true表示混合定位中包含gps定位，false表示纯网络定位，默认是true
			 */
			// Location API定位采用GPS和网络混合定位方式，时间最短是2000毫秒
			aMapManager.requestLocationUpdates(LocationProviderProxy.AMapNetwork, 2000, 10, this);
		}
	}

	@Override
	public void deactivate() {
		mListener = null;
		if (aMapManager != null) {
			aMapManager.removeUpdates(this);
			aMapManager.destory();
		}
		aMapManager = null;
	}

	/**
	 * 方法必须重写
	 */
	@Override
	public void onResume() {
		super.onResume();
		mapView.onResume();
	}

	/**
	 * 方法必须重写
	 */
	@Override
	public void onPause() {
		super.onPause();
		mapView.onPause();
		deactivate();
	}

	/**
	 * 方法必须重写
	 */
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mapView.onSaveInstanceState(outState);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mapView.onDestroy();
	}

	@Override
	public boolean onMarkerClick(Marker arg0) {
		return false;
	}

	@Override
	public void onLocationChanged(Location location) {

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {

	}

	@Override
	public void onProviderEnabled(String provider) {

	}

	@Override
	public void onProviderDisabled(String provider) {

	}

	@Override
	public void onMapLoaded() {

	}

	@Override
	public View getInfoContents(Marker arg0) {
		return null;
	}

	@Override
	public View getInfoWindow(Marker marker) {
		LayoutInflater inflater = LayoutInflater.from(NoteApplication.appContext);
		View infoWindow = inflater.inflate(R.layout.custom_info_window, null);
		renderInfoWindow(marker, infoWindow);
		return infoWindow;
	}



	@Override
	public void onClick(View v) {

	}

}
