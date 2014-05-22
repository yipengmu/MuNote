/**
 * 
 */
package com.laomu.note.ui.act.camera;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.laomu.note.R;
import com.laomu.note.common.Camera.DataLoadThread;
import com.laomu.note.ui.adapter.PicturesGridViewAdatper;
import com.laomu.note.ui.base.NoteBaseActivity;

/**
 * @author luoyuan.myp
 * 
 *         2014-5-21
 */
public class CameraPicturesActivity extends NoteBaseActivity implements OnItemClickListener {

	private String mTitle = "相机图片";
	private GridView mGridView;
	private PicturesGridViewAdatper mGridViewAdapter;
	private ArrayList<String> filePaths;
	private RelativeLayout mLoadingView;
	private static int TAG_LOADED_COMPELETED = 0;
	private DataLoadedHandler mDataLoadedHandler = new DataLoadedHandler(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pictures_grid_layout);
		
		initData();
		initViews();
	}

	private void initViews() {
		findViews();		
		setTitle(mTitle);
		mGridView.setOnItemClickListener(this);
	}

	private void initData() {
		filePaths = getIntent().getStringArrayListExtra("filePaths");
	}

	private void findViews() {
		mGridView = (GridView) findViewById(R.id.gv_pictures);
		mLoadingView = (RelativeLayout) findViewById(R.id.rl_loading_view);
		mGridViewAdapter = new PicturesGridViewAdatper();
		mGridView.setAdapter(mGridViewAdapter);
		
		showLoadingImageView();
		new Thread(new DataLoadThread(filePaths,mDataLoadedHandler)).start();
	}

	private void showLoadingImageView() {
		mLoadingView.setVisibility(View.VISIBLE);
	}

	private void hideLoadingImageView() {
		AlphaAnimation aAlphaAnimation = new AlphaAnimation(1, 0);
		aAlphaAnimation.setDuration(500);
		mLoadingView.startAnimation(aAlphaAnimation);		
		mLoadingView.setVisibility(View.GONE);
	}

	private void handleDataLoaded() {
		hideLoadingImageView();
		if (filePaths != null) {
			mGridViewAdapter.setDatasource(this, filePaths);
			mGridViewAdapter.notifyDataSetChanged();
		}
	}

	
	static class DataLoadedHandler extends Handler {
		WeakReference<CameraPicturesActivity> mActivity;

		DataLoadedHandler(CameraPicturesActivity activity) {
			mActivity = new WeakReference<CameraPicturesActivity>(activity);
		}

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == TAG_LOADED_COMPELETED) {
				CameraPicturesActivity refActivity = mActivity.get();
				refActivity.handleDataLoaded();
			}
		}
	}


	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Toast.makeText(getApplicationContext(), "p= " + position, Toast.LENGTH_SHORT).show();
	}

}
