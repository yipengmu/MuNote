/**
 * 
 */
package com.laomu.note.data.database;

import java.sql.SQLException;
import java.util.List;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.laomu.note.data.model.LocationBean;
import com.laomu.note.data.model.NoteBean;
import com.laomu.note.ui.NoteApplication;

/**
 * @author luoyuan.myp
 *
 * 2014-4-15
 */
public class OrmDbManeger {
	private static OrmDbManeger ins = null;
	private Context mContext;
	private Dao<NoteBean, Integer> noteDao = null;
	private Dao<LocationBean, Integer> locationDao = null;
	private TableHelper4NoteBean mTableHelper4NoteBean;
	private TableHelper4LocationBean mTableHelper4LocationBean;
	
	private OrmDbManeger(Context c){
		mContext = c;
		mTableHelper4NoteBean = new TableHelper4NoteBean(mContext);
		mTableHelper4LocationBean = new TableHelper4LocationBean(mContext);
	}
	
	public static OrmDbManeger getInstance(){
		if(null == ins){
			ins = new OrmDbManeger(NoteApplication.appContext);
		}
		return ins ;
	}
	
	public void addNote(NoteBean note){
		try {
			noteDao = mTableHelper4NoteBean.getDao(NoteBean.class);
			noteDao.create(note);
			noteDao.clearObjectCache();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void delNote(NoteBean note){
		try {
			noteDao = mTableHelper4NoteBean.getDao(NoteBean.class);
			noteDao.delete(note);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void updateNote(NoteBean note){
		try {
			noteDao = mTableHelper4NoteBean.getDao(NoteBean.class);
			noteDao.update(note);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<NoteBean> queryNote(){
		List<NoteBean> list = null;
		try {
			noteDao = mTableHelper4NoteBean.getDao(NoteBean.class);
			list = noteDao.queryForAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public void addLocation(LocationBean loc){
		try {
			locationDao = mTableHelper4LocationBean.getDao(LocationBean.class);
			locationDao.create(loc);
			locationDao.clearObjectCache();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
