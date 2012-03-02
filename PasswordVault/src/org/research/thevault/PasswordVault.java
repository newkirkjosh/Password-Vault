package org.research.thevault;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ToggleButton;
import android.view.View;
import android.view.View.OnClickListener;


public class PasswordVault extends Activity implements OnClickListener, Constants{
    
	Cursor cursor;
	SQLiteDatabase db;
	EditText pWord;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.password_vault);
        
        pWord = (EditText) findViewById( R.id.login_password1 );
        Button loginButton = (Button) findViewById( R.id.login_button );
        loginButton.setOnClickListener( this );
        
        Button aboutButton = (Button) findViewById( R.id.about_button );
        aboutButton.setOnClickListener( this );
        
        Button displayButton = (Button) findViewById( R.id.display_button );
        displayButton.setOnClickListener( this );
        
    }
    @Override
    public void onDestroy() {
        if( cursor != null )
            cursor.close();
        if( db != null )
            db.close();
        super.onDestroy();
    }
    
	public void onClick( View v ){
    	switch ( v.getId() ){
    	case R.id.login_button:
    		validateLogin();
    		if( cursor != null && cursor.moveToFirst() ){
    			ToggleButton intentSwitch = (ToggleButton) findViewById( R.id.intent_grabber );
    			if( !intentSwitch.isChecked() ){
	    			Intent sites = new Intent( this, SitesList.class );
	    			startActivity( sites );
	    			pWord.setText( "" );
    			}
    			// Need receiver for intent
    			else{
    				Intent grab = new Intent( this, PassTaken.class );
    				grab.putExtra( "Password", pWord.getText().toString() );
    				startActivity( grab );
    			}
    		}
    		else{
    			Intent badPass = new Intent( this, BadPass.class );
	    		badPass.putExtra("Password", pWord.getText().toString());
    			startActivity( badPass );
    		}
    		break;
    		
    	case R.id.about_button:
    		Intent about = new Intent( this, About.class );
    		startActivity( about );
    		break;
    	case R.id.display_button:
    		Intent display = new Intent( this, DisplayPassword.class );
    		display.putExtra( "PASSWORD",  pWord.getText().toString() );
    		startActivity( display );
    		break;
    	}
    }
    public void validateLogin(){
    	PasswordTable pt = new PasswordTable( this );
    	db = pt.getReadableDatabase();
    	cursor = null;
    	try{
			cursor = db.rawQuery( "SELECT "+ PWORD + " FROM " + PASS_TABLE_NAME
					+ " WHERE " + PWORD + "='" + pWord.getText().toString() + "'", null);
			startManagingCursor( cursor );
    	}
    	catch( Exception e ){
    	}
    	
    }		
}