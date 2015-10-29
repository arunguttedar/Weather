package com.example.weather.json;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CountMGTDB extends SQLiteOpenHelper {

	public static String DATABASE_NAME = "CountMGTDB.db";

	public CountMGTDB(Context context) {
		super(context, DATABASE_NAME, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table CountMGT " + "(id integer primary key,"
				+ "friendID text,count text)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS CountMGT");
		onCreate(db);
	}

	// Insert record into CountMGT table (for inserting key)
	public boolean insertCount(String friendID,String count) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();

		contentValues.put("friendID", friendID);
		contentValues.put("count", count);
		db.insert("CountMGT", null, contentValues);
		return true;
	}

	// getting  form CountMGT table
	public Cursor getCount() {
	
		String selectQuery = "select friendID,count from CountMGT";
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		
		return cursor;
	}
	public void upDate_DataCount(String friendID, String count) {
		
		SQLiteDatabase db = this.getWritableDatabase();
		
		db.execSQL("update CountMGT set count='" + count + "' where friendID='" + friendID+"'" );
		db.close();

	}
}
