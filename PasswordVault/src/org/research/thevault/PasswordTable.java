package org.research.thevault;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//sql table for the passwords
public class PasswordTable extends SQLiteOpenHelper implements Constants{
	
	private static final String DATABASE_NAME = "login.db" ;
	private static final int DATABASE_VERSION = 1;
	
	/** Create a helper object for the Events database */
	public PasswordTable( Context ctx ) {
		super( ctx, DATABASE_NAME, null, DATABASE_VERSION );
	}
	
    //create the table
	@Override
	public void onCreate(SQLiteDatabase db) {
	db.execSQL("CREATE TABLE " + PASS_TABLE_NAME + " (" + _ID
	+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + PWORD + " TEXT);" );
	}

    //updaet and recreate the table
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion,
	int newVersion) {
	db.execSQL("DROP TABLE IF EXISTS " + PASS_TABLE_NAME);
	onCreate(db);
	}
}

