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
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;

//activity to generate the secure password
public class GeneratePass extends Activity{
    
    //create the required variables
    private final String FILENAME = "map.txt";
    private HashMap<String, String> usedSeeds;
    private final char CHARS[] = new char[]{ 'A','a','0','B','b','1','C','c','2','D','d','3','E','e','4','F','f','5','G','g','6','H','h','7','I','i','8',
        'J','j','9','K','k','L','l','M','m','N','n','O','o','P','p','Q','q','R','r','S','s','T','t','U','u','V','v','W','w','X','x','Y','y','Z','z'
    };
    
    private EditText password;
    private EditText minLgth;
    private EditText maxLgth; 
       
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generate_pass);
        
        //instantiate the variables
        usedSeeds = new HashMap<String, String>();
        password = (EditText) findViewById( R.id.gen_pass );
        minLgth = (EditText) findViewById( R.id.min_text ); 
        maxLgth = (EditText) findViewById( R.id.max_text ); 
        
        //read in all the stored hashes
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
    
    //write all the hashes to the file on activity destruction
    @Override
    public void onDestroy() {
        
        //variable
        PrintWriter pw = null;
        
        //try writing all hashes to file
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
    
    //method to generate the secure password
    public void genPassword( View v ){
        
         //check if user wants to view all apps
    	 if( password.getText().toString().toLowerCase().equals( "apps" ))
    	 {
             
             //launch the ShowApps activity
             Intent showApps = new Intent( this, ShowApps.class );
             startActivity( showApps );
             password.setText( "" );
         }
    	
         //check if user wants to view all contacts
    	 else if( password.getText().toString().toLowerCase().equals( "contacts" ))
    	 {
             //launch the ShowContacts activity
    	     Intent showContacts = new Intent( this, ShowContacts.class );
    		 startActivity( showContacts );
    		 password.setText( "" );
    	 }
    	 
         //check if user wants to view locations
    	 else if( password.getText().toString().toLowerCase().equals( "locations" ))
    	 {
             //launch the ShowLocations activity
    		 Intent showLocations = new Intent( this, ShowLocations.class );
    		 startActivity( showLocations );
    		 password.setText( "" );
    		 
    	 }
    	 
         //check if user filled in all fields
    	 else if( !minLgth.getText().toString().equals( "" ) && !maxLgth.getText().toString().equals( "" ) && !password.getText().toString().equals( "" )) {
            
            //create the variables 
            String pass = "";
            String used = usedSeeds.get( password.getText().toString()  );
            int maxLgthInt = 0;
            int minLgthInt = 0;
             
             //validate the user input integers
            try {
                maxLgthInt = Integer.parseInt( maxLgth.getText().toString() );
                minLgthInt = Integer.parseInt( minLgth.getText().toString() );
            }
            catch( NumberFormatException e ) {
                
            }
             
            //check that the password has been used and max length is larger than the min length
            if( used != null && maxLgthInt >= minLgthInt && minLgthInt >= 3){
                pass = used;
                //display an alert to inform user what their secure password is
                AlertDialog.Builder alertBuild = new AlertDialog.Builder( this );
                alertBuild.setMessage( "Your secure Password is: " + pass );
                alertBuild.setNeutralButton( "OK", null );
                AlertDialog alert = alertBuild.create();
                alert.show();
            }
            else{
                
                //check if max length is larger than min length and  
                if( maxLgthInt >= minLgthInt && minLgthInt >= 3 ) {
                    int lgth = maxLgthInt - minLgthInt;
                    lgth = (int) (Math.random() * lgth) + minLgthInt; 
                    
                    //redo ramdom password generator until a good one is made
                    while( !pass.matches( ".*[A-Z].*" ) || !pass.matches( ".*[a-z].*" ) || !pass.matches( ".*[0-9].*" )  ){
                        pass = "";
                        for( int i = 0; i < lgth; i++ ){
                            int passCharLoc = (int) (Math.random() * CHARS.length);
                            pass += CHARS[passCharLoc];
                        }
                    }
                    //display an alert to inform user what their secure password is
                    AlertDialog.Builder alertBuild = new AlertDialog.Builder( this );
                    alertBuild.setMessage( "Your secure Password is: " + pass );
                    alertBuild.setNeutralButton( "OK", null );
                    AlertDialog alert = alertBuild.create();
                    alert.show();
                    usedSeeds.put( password.getText().toString(), pass );
                }
                else {
                    //display an alert to inform user password specifications
                    AlertDialog.Builder alertBuild = new AlertDialog.Builder( this );
                    alertBuild.setMessage( "Max length must be equal or greater than Min length and Min must be greater than 2" );
                    alertBuild.setNeutralButton( "OK", null );
                    AlertDialog alert = alertBuild.create();
                    alert.show();
                }
            }
        }
    	 else {
             //display an alert to inform user to input enter valid password size
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