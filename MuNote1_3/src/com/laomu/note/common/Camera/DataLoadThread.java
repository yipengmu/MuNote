package com.laomu.note.common.Camera;

import java.util.ArrayList;
import java.util.List;

import com.laomu.note.common.CommonDefine;

import android.os.Handler;

public class DataLoadThread implements Runnable {

	private List<String> filePaths;
	private Handler mDataLoadedHandler;

	public DataLoadThread(List<String> filePaths) {
		this.filePaths = filePaths;
	}

	public DataLoadThread(ArrayList<String> filePaths,Handler h) {
		this.filePaths = filePaths;
		mDataLoadedHandler = h;
	}
	
	public void setHandler(Handler h) {
		mDataLoadedHandler = h;
	}

	@Override
	public void run() {
		ImageManeger.setImagesLruCache(filePaths);
		if (mDataLoadedHandler != null) {
			mDataLoadedHandler.sendEmptyMessage(CommonDefine.TAG_LOADED_COMPELETED);
		}
	}
}
