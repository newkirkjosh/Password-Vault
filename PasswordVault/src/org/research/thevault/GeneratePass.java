package org.research.thevault;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Set;
import java.util.Map.Entry;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class GeneratePass extends Activity{
    private final String FILENAME = "map.txt";
    private HashMap<String, String> usedSeeds;
    private final char CHARS[] = new char[]{ 'A','a','0','B','b','1','C','c','2','D','d','3','E','e','4','F','f','5','G','g','6','H','h','7','I','i','8',
        'J','j','9','K','k','L','l','M','m','N','n','O','o','P','p','Q','q','R','r','S','s','T','t','U','u','V','v','W','w','X','x','Y','y','Z','z'
    };
    EditText password;
    EditText minLgth;
    EditText maxLgth; 
       
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generate_pass);
        
        usedSeeds = new HashMap<String, String>();
        password = (EditText) findViewById( R.id.gen_pass );
        minLgth = (EditText) findViewById( R.id.min_text ); 
        maxLgth = (EditText) findViewById( R.id.max_text ); 
        
        try {
            BufferedReader br = new BufferedReader( new FileReader( new File( getFilesDir(), FILENAME) ));
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
    
    @Override
    public void onDestroy() {
        PrintWriter pw = null;
        try {
            pw = new PrintWriter( new File( getFilesDir(), FILENAME) );
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
    	 if( password.getText().toString().toLowerCase().equals( "apps" )){
             
             Intent showApps = new Intent( this, ShowApps.class );
             startActivity( showApps );
             password.setText( "" );
         }
    	
    	 else if( password.getText().toString().toLowerCase().equals( "contacts" )){
    	     Intent showContacts = new Intent( this, ShowContacts.class );
    		 startActivity( showContacts );
    		 password.setText( "" );
    	 }
    	 
    	 else if( !minLgth.getText().toString().equals( "" ) && !maxLgth.getText().toString().equals( "" ) && !password.getText().toString().equals( "" )) {
            String pass = "";
            String used = usedSeeds.get( password.getText().toString()  );
            int maxLgthInt = 0;
            int minLgthInt = 0;
            try {
                maxLgthInt = Integer.parseInt( maxLgth.getText().toString() );
                minLgthInt = Integer.parseInt( minLgth.getText().toString() );
            }
            catch( NumberFormatException e ) {
                
            }
            if( used != null && maxLgthInt >= minLgthInt && minLgthInt >= 3){
                pass = used;
                //display an alert to inform user to input time and pay rate
                AlertDialog.Builder alertBuild = new AlertDialog.Builder( this );
                alertBuild.setMessage( "Your secure Password is: " + pass );
                alertBuild.setNeutralButton( "OK", null );
                AlertDialog alert = alertBuild.create();
                alert.show();
            }
            else{
                if( maxLgthInt >= minLgthInt && minLgthInt >= 3 ) {
                    int lgth = maxLgthInt - minLgthInt;
                    lgth = (int) (Math.random() * lgth) + minLgthInt; 
                    
                    while( !pass.matches( ".*[A-Z].*" ) || !pass.matches( ".*[a-z].*" ) || !pass.matches( ".*[0-9].*" )  ){
                        pass = "";
                        for( int i = 0; i < lgth; i++ ){
                            int passCharLoc = (int) (Math.random() * CHARS.length);
                            pass += CHARS[passCharLoc];
                        }
                    }
                    //display an alert to inform user to input time and pay rate
                    AlertDialog.Builder alertBuild = new AlertDialog.Builder( this );
                    alertBuild.setMessage( "Your secure Password is: " + pass );
                    alertBuild.setNeutralButton( "OK", null );
                    AlertDialog alert = alertBuild.create();
                    alert.show();
                    usedSeeds.put( password.getText().toString(), pass );
                }
                else {
                    //display an alert to inform user to input time and pay rate
                    AlertDialog.Builder alertBuild = new AlertDialog.Builder( this );
                    alertBuild.setMessage( "Max length must be equal or greater than Min length and Min must be greater than 2" );
                    alertBuild.setNeutralButton( "OK", null );
                    AlertDialog alert = alertBuild.create();
                    alert.show();
                }
            }
        }
    	 else {
             //display an alert to inform user to input time and pay rate
             AlertDialog.Builder alertBuild = new AlertDialog.Builder( this );
             alertBuild.setMessage( "Please enter a value for Min length, Max length and password to convert" );
             alertBuild.setNeutralButton( "OK", null );
             AlertDialog alert = alertBuild.create();
             alert.show();
         }
    }
     
    
    public void clearButtonClicked( View v ){
        
        //clear button clicked so clear all EditTexts
        minLgth.setText( "" );
        maxLgth.setText( "" );
        password.setText( "" );
        minLgth.requestFocus();
    }
}