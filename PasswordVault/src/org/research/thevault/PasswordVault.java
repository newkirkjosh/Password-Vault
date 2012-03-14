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

//default activity for the app, has an intentional sql injection
public class PasswordVault extends Activity implements OnClickListener, Constants{
    
    //create the variables
	private Cursor cursor;
	private SQLiteDatabase db;
	private EditText pWord;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.password_vault);
        
        //instaniate the variables and add an onClick listener
        pWord = (EditText) findViewById( R.id.login_password1 );
        Button loginButton = (Button) findViewById( R.id.login_button );
        loginButton.setOnClickListener( this );
        
        Button aboutButton = (Button) findViewById( R.id.about_button );
        aboutButton.setOnClickListener( this );
        
        Button displayButton = (Button) findViewById( R.id.display_button );
        displayButton.setOnClickListener( this );
        
    }
    
    //catch the activity being destroyed and close the cursor
    @Override
    public void onDestroy() {
        if( cursor != null )
            cursor.close();
        if( db != null )
            db.close();
        super.onDestroy();
    }
    
    //method to catch the user clicking buttons
	public void onClick( View v ){
    	
        //find out what button was clicked
        switch ( v.getId() ){
                
        //check if user wants to log in
    	case R.id.login_button:
    		validateLogin();
                
            //check if user entered a valid password
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
                
            //display the badPass activity
    		else{
    			Intent badPass = new Intent( this, BadPass.class );
	    		badPass.putExtra("Password", pWord.getText().toString());
    			startActivity( badPass );
    		}
    		break;
    		
        //display the about activity
    	case R.id.about_button:
    		Intent about = new Intent( this, About.class );
    		startActivity( about );
    		break;
                
        //display the displayPassword activity
    	case R.id.display_button:
    		Intent display = new Intent( this, DisplayPassword.class );
    		display.putExtra( "PASSWORD",  pWord.getText().toString() );
    		startActivity( display );
    		break;
    	}
    }
    
    //verify the user is granted to log in, sql injection lies here
    public void validateLogin(){
        
        //create the table
    	PasswordTable pt = new PasswordTable( this );
    	db = pt.getReadableDatabase();
    	cursor = null;
        
        //try and get password and check if logged in
    	try{
			cursor = db.rawQuery( "SELECT "+ PWORD + " FROM " + PASS_TABLE_NAME
					+ " WHERE " + PWORD + "='" + pWord.getText().toString() + "'", null);
			startManagingCursor( cursor );
    	}
    	catch( Exception e ){
    	}
    	
    }		
}