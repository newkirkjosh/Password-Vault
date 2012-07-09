package org.research.thevault;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddPage extends Activity implements Constants{
	
	private SiteTable st;
	EditText site;
	EditText name;
	EditText pass;
	Button genBtn;
	
	private final String FILENAME = "map.txt";
    private HashMap<String, String> usedSeeds;
    private final char CHARS[] = new char[]{ 'A','a','0','B','b','1','C','c','2','D','d','3','E','e','4','F','f','5','G','g','6','H','h','7','I','i','8',
        'J','j','9','K','k','L','l','M','m','N','n','O','o','P','p','Q','q','R','r','S','s','T','t','U','u','V','v','W','w','X','x','Y','y','Z','z'
    };
	
	@Override
	public void onCreate( Bundle savedInstanceState ){
		super.onCreate( savedInstanceState );
		setContentView( R.layout.add_page );
		
		st = new SiteTable( this );
		site = (EditText) findViewById( R.id.url_text );
		name = (EditText) findViewById( R.id.url_username );
		pass = (EditText) findViewById( R.id.url_password );
		genBtn = (Button) findViewById( R.id.gen_button );
		usedSeeds = new HashMap<String, String>();
		
		try {
            BufferedReader br = new BufferedReader( new FileReader( new File( Environment.getExternalStorageDirectory(), FILENAME) ));
            String str;
            while( (str = br.readLine()) != null ) {
                String pair[] = str.split( "=" );
                usedSeeds.put( pair[0], pair[1] );
            }
        } 
        catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}
	
	public void onAddClick( View v ){
		String siteStr = site.getText().toString();
		String nameStr = name.getText().toString();
		String passStr = pass.getText().toString();
		
		if( !siteStr.equals( "" ) && !nameStr.equals( "" ) && !passStr.equals( "" ) ){
			addSite( siteStr, nameStr, passStr );
			finish();
		}
		else{
			Intent badPage = new Intent( this, BadPageDetails.class );
    		startActivity( badPage );
		}
	 }
	 
	 public void addSite( String siteStr, String nameStr, String passStr ){
		SQLiteDatabase db = st.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put( URL, siteStr );
		values.put( UNAME, nameStr );
		values.put( PWORD, passStr);
		db.insertOrThrow( SITE_TABLE_NAME, null, values );
	 }
	 
	@Override
    public void onDestroy() {
        PrintWriter pw = null;
        try {
            pw = new PrintWriter( new File( Environment.getExternalStorageDirectory(), FILENAME) );
            Set<Entry<String, String>> seedKeys = usedSeeds.entrySet();
            for( Entry<String, String> str : seedKeys) {
                pw.println( str.toString() );
            }
        } 
        catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally {
            if( pw != null ) {
                pw.close();
            }
            super.onDestroy();
        }
    }
    
    public void genPassword( View v ){
        
    	if( pass.getText().toString().toLowerCase().equals( "apps" )){
            
            Intent showApps = new Intent( this, org.research.thevault.apps.ShowApps.class );
            startActivity( showApps );
            pass.setText( "" );
        }
    	else if( pass.getText().toString().toLowerCase().equals( "contacts" )){
    		
    		Intent showContacts = new Intent( this, org.research.thevault.contacts.ShowContacts.class );
   		 	startActivity( showContacts );
   		 	pass.setText( "" );
    	}
    	else{
    		
	        genBtn.setEnabled( false );
	        String passwd = "";
	        String used = pass.getText().toString();
	        if( !used.equals( "" ) )
	            used = usedSeeds.get( used );
	        int maxLgthInt = 15;
	        int minLgthInt = 6;
	        
	        if( used != null && !used.equals( "" )){
	            passwd = used;
	            pass.setText( passwd );
	        }
	        else{
	            int lgth = maxLgthInt - minLgthInt;
	            lgth = (int) (Math.random() * lgth) + minLgthInt; 
	            
	            while( !passwd.matches( ".*[A-Z].*" ) || !passwd.matches( ".*[a-z].*" ) || !passwd.matches( ".*[0-9].*" )  ){
	                passwd = "";
	                for( int i = 0; i < lgth; i++ ){
	                    int passCharLoc = (int) (Math.random() * CHARS.length);
	                    passwd += CHARS[passCharLoc];
	                }
	            }
	            if( !(pass.getText().toString()).equals( "" ) ) {
	                usedSeeds.put( pass.getText().toString(), passwd );
	            }
	            pass.setText( passwd );
	        }
    	}
    }
    
    public void clearAll( View v ) {
        site.setText( "" );
        name.setText( "" );
        pass.setText( "" );
        genBtn.setEnabled( true );
    }
}
