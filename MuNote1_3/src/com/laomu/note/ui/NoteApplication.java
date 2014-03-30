/**
 * 
 */
package com.laomu.note.ui;

import com.laomu.note.common.lbs.LocationInfoManeger;

import android.app.Application;
import android.content.Context;

/**
 * @author luoyuan.myp
 *
 * 2014-3-28
 */
public class NoteApplication extends Application{


	private static LocationInfoManeger mLBSManeger = null;
	public static Context appContext = null;
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		initNoteApplication();
		
	}
	private void initNoteApplication() {
		appContext = getApplicationContext();
		initLBS();
	}
	private void initLBS() {
		mLBSManeger = LocationInfoManeger.getInstance();	
		mLBSManeger.activeLocate(getApplicationContext());
	}

	public static LocationInfoManeger getLBSManeger(){
		return mLBSManeger;
	}
}
