package org.research.thevault;

import android.provider.BaseColumns;

//constants that will be used in the database
public interface Constants extends BaseColumns {
	
	public static final String PASS_TABLE_NAME = "password" ;
	public static final String SITE_TABLE_NAME = "sites" ;
	
	// Columns in the Events database
	public static final String PWORD = "password";
	public static final String URL = "url";
	public static final String UNAME = "username";
}
