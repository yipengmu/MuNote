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
	private TableHelper4db mTableHelper4db;
	
	private OrmDbManeger(Context c){
		mContext = c;
		mTableHelper4db = new TableHelper4db(mContext);
	}
	
	public static OrmDbManeger getInstance(){
		if(null == ins){
			ins = new OrmDbManeger(NoteApplication.appContext);
		}
		return ins ;
	}
	
	public void addNote(NoteBean note){
		try {
			noteDao = mTableHelper4db.getDao(NoteBean.class);
			noteDao.create(note);
			noteDao.clearObjectCache();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void delNote(NoteBean note){
		try {
			noteDao = mTableHelper4db.getDao(NoteBean.class);
			noteDao.delete(note);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void updateNote(NoteBean note){
		try {
			noteDao = mTableHelper4db.getDao(NoteBean.class);
			noteDao.update(note);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<NoteBean> queryNote(){
		List<NoteBean> list = null;
		try {
			noteDao = mTableHelper4db.getDao(NoteBean.class);
			list = noteDao.queryForAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public List<NoteBean> queryNote(int searchId){
		List<NoteBean> list = null;
		try {
			noteDao = mTableHelper4db.getDao(NoteBean.class);
			list = noteDao.queryForEq("id",searchId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	public int addLocation(LocationBean loc){
		int locationId = -1;
		try {
			locationDao = mTableHelper4db.getDao(LocationBean.class);
			//向location 表中先插入一条记录
			locationDao.create(loc);
			//在从表中尝试取出此条记录，并拿到主键的key,即locationId
			List<LocationBean> resultList = locationDao.queryForMatching(loc);
			if(resultList != null && resultList.size() == 1){
				locationId = resultList.get(0).id;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return locationId;
	}
	
	public List<LocationBean> queryLocation(int searchId){
		List<LocationBean> list = null;
		try {
			locationDao = mTableHelper4db.getDao(LocationBean.class);
			list = locationDao.queryForEq("id",searchId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

//	public void queryLocationId(LocationBean mLocation) {
//		try {
//			noteDao = mTableHelper4db.getDao(LocationBean.class);
//			noteDao.queryForId(0);
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//
//	}
	
	
}
