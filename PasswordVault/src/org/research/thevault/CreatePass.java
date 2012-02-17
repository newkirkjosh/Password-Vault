package org.research.thevault;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class CreatePass extends Activity implements OnClickListener, Constants{

	PasswordTable pw;
	EditText pWord;
	EditText pWordConf;
	
	@Override
	public void onCreate( Bundle savedInstanceState ){
		super.onCreate( savedInstanceState );
		pw = new PasswordTable( this );
		if( checkPassword() ){
			Intent passwordVault = new Intent( this, PasswordVault.class );
    		startActivity( passwordVault );
			finish();
		}
		else{
			setContentView( R.layout.create_password );
			pWord = (EditText) findViewById( R.id.password );
	        pWordConf = (EditText) findViewById( R.id.conf_password );
		}
	}
	
	public void onClick( View v ){
    	if( !pWord.getText().toString().equals("") && (pWord.getText().toString()).equals( pWordConf.getText().toString()) ){
    		addPassword( pWord.getText().toString() );
    		Intent passwordVault = new Intent( this, PasswordVault.class );
    		startActivity( passwordVault );
    		finish();
    	}
    	else{
    		pWord.setText( "" );
    		pWordConf.setText( "" );
    		pWord.requestFocus();
    		Intent fail = new Intent( this, CreateFail.class );
    		startActivity( fail );
    		
    	}
    }
	
	private void addPassword( String password ){
		SQLiteDatabase db = pw.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put( PWORD, password );
		db.insertOrThrow( PASS_TABLE_NAME, null, values );
		db.close();
	}
	
	public boolean checkPassword(){
		SQLiteDatabase db = pw.getReadableDatabase();
    	
    	Cursor hasPass = db.rawQuery( "SELECT * FROM " + PASS_TABLE_NAME, null);
    	boolean hasElement = hasPass.moveToFirst();
    	db.close();
    	hasPass.close();
        return hasElement;
    }
}
