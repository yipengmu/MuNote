/**
 * 
 */
package com.laomu.note.data;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * @author luoyuan.myp
 * 
 *         2013-6-22
 */
public class DBManager {
	private DatabaseHelper helper;
	private SQLiteDatabase db;
	private static DBManager instance ;
	/**
	 * construct
	 */
	private DBManager(Context c) {
		helper = new DatabaseHelper(c);
		db = helper.getWritableDatabase();
	}

	public static DBManager instance(Context c){
		if(instance == null){
			instance = new DBManager(c);
		}
		return instance;
	}
	
	/**
	 * add database cursor
	 * */
	public void add(List<NoteBean> noteList) {
		db.beginTransaction(); 
		try {
			for (NoteBean note : noteList) {
				db.execSQL("INSERT INTO note VALUES(null, ?, ?, ?)", new Object[] {
						note.note_title, note.note_content, note.note_time });
			}
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}
	}

	/**
	 * add database cursor
	 * */
	public void add(NoteBean note) {
		db.beginTransaction(); 
		try {
			db.execSQL("INSERT INTO note VALUES(null, ?, ?, ?)", new Object[] {
						note.note_title, note.note_content, note.note_time });
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}
	}
	/**
	 * query all notebeans, return cursor
	 * 
	 * @return Cursor
	 */
	public Cursor queryTheCursor() {
		Cursor c = db.rawQuery("SELECT * FROM note", null);
		return c;
	}

	/**
	 * close database
	 */
	public ArrayList<NoteBean> queryAllData() {
		ArrayList<NoteBean> persons = new ArrayList<NoteBean>();
		Cursor c = queryTheCursor();
		while (c.moveToNext()) {
			NoteBean person = new NoteBean();
			person.note_index = c.getInt(c.getColumnIndex("_id"));
			person.note_title = c.getString(c.getColumnIndex("title"));
			person.note_content = c.getString(c.getColumnIndex("content"));
			person.note_time = c.getString(c.getColumnIndex("time"));
			persons.add(person);
		}
		c.close();
		return persons;
	}

	/**
	 * update database by notebean
	 */
	public void updateAge(NoteBean note) {
		ContentValues cv = new ContentValues();
		cv.put("_id", note.note_index);
		cv.put("title", note.note_title);
		cv.put("content", note.note_title);
		cv.put("time", note.note_time);
		String sql = String.format(
				"update note set title =  '%s',content = '%s',time = '%s' where _id = '%s'",
				note.note_title, note.note_content, note.note_time, note.note_index);
		db.execSQL(sql);
//		db.update("note", cv, "_id = ?", new String[] { String.valueOf(note.note_title) });
	}

	/**
	 * delate datebase by notebean
	 */
	public void deleteOldNote(NoteBean note) {
		db.delete("note", "title = ?", new String[] { note.note_title });
	}

	/**
	 * delate datebase by notebean
	 */
	public void deleteAllNotes() {
		db.execSQL("DELETE FROM note");
	}

	/**
	 * close database
	 */
	public void closeDB() {
		db.close();
	}

	/**
	 * @param mNoteBean
	 */
	public void addNoteBean(NoteBean note) {
		db.beginTransaction(); 
		try {
			db.execSQL("INSERT INTO note VALUES(null, ?, ?, ?)", new Object[] { note.note_title,
					note.note_content, note.note_time });
			db.setTransactionSuccessful(); 
		} finally {
			db.endTransaction(); 
		}
	}
}
