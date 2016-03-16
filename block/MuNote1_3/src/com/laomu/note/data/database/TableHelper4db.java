package com.laomu.note.data.database;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.laomu.note.data.model.LocationBean;
import com.laomu.note.data.model.NoteBean;

public class TableHelper4db extends OrmLiteSqliteOpenHelper {
	
	public TableHelper4db(Context context){
		super(context, DBConfig.DATABASE_NAME, null, DBConfig.DATABASE_VERSION);
	}
	
	public TableHelper4db(Context context, String databaseName, CursorFactory factory, int databaseVersion){
		super(context, DBConfig.DATABASE_NAME, null, DBConfig.DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase arg0, ConnectionSource arg1) {
		try {
			TableUtils.createTableIfNotExists(connectionSource, LocationBean.class);
			TableUtils.createTableIfNotExists(connectionSource, NoteBean.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, ConnectionSource arg1, int arg2,
			int arg3) {
		try {
			TableUtils.dropTable(connectionSource, LocationBean.class, true);
			TableUtils.dropTable(connectionSource, NoteBean.class, true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onOpen(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		super.onOpen(db);
	}

}
