/**
 * 
 */
package com.laomu.note.ui.act.camera;

import java.util.ArrayList;

import android.database.DataSetObserver;
import android.os.Bundle;
import android.os.Handler;
import android.widget.GridView;

import com.laomu.note.R;
import com.laomu.note.ui.adapter.PicturesGridViewAdatper;
import com.laomu.note.ui.base.NoteBaseActivity;

/**
 * @author luoyuan.myp
 *
 * 2014-5-21
 */
public class CameraPicturesActivity extends NoteBaseActivity{

	private GridView mGridView;
	private PicturesGridViewAdatper mGridViewAdapter;
	private ArrayList<String> filePaths;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pictures_grid_layout);
		initData();
		findViews();
	}
	
	private void initData() {
		filePaths = getIntent().getStringArrayListExtra("filePaths");
	}
	
	private void findViews() {
		mGridView = (GridView) findViewById(R.id.gv_pictures);
		mGridViewAdapter = new PicturesGridViewAdatper();
		// TODO Auto-generated method stub
//		for(){
//			
//		}
//		new Handler().postDelayed(new Runnable() {
//			
//			@Override
//			public void run() {
//				
//			}
//		}, 100);
		if(filePaths != null){
			mGridViewAdapter.setDatasource(this,filePaths);
		}
		mGridView.setAdapter(mGridViewAdapter);
		
	}
}
