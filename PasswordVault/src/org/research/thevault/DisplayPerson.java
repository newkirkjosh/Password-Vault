package org.research.thevault;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.TextView;
import android.widget.ImageView;

//activity to display details of a person clicked
public class DisplayPerson extends Activity{
    @Override
    protected void onCreate( Bundle savedInstanceState ){
        super.onCreate( savedInstanceState );
        setContentView( R.layout.display_contact );
        
        //create the variables
        Persons person = (Persons) this.getIntent().getSerializableExtra( "PERSON" );
        TextView name = (TextView) findViewById( R.id.textName );
        TextView email = (TextView) findViewById( R.id.textEmail );
        TextView phone = (TextView) findViewById( R.id.textPhone );
        ImageView photo = (ImageView) findViewById( R.id.ContactBadge );
        
        //set the text in this textview
        name.setText( person.getName() );
        
        //load person photo if it exists
        if( person.getThumbData() != null ) {
            Bitmap map = BitmapFactory.decodeByteArray(person.getThumbData(), 0, person.getThumbData().length );
            photo.setImageBitmap( map );
        }
        
        //set default photo
        else {
            photo.setImageResource( R.drawable.droid );
        }
        
        //Check if contant has a phone number
        if( person.getNumber() == null || person.getNumber().equals( "" ) )
        {
        	phone.setText("N/A");
        }
        
        //display phone #
        else
        {
        	phone.setText(person.getNumber());
        }
        
        //check if person has an email
        if( person.getEmail() == null || person.getEmail().equals( "" ) )
        {
            email.setText( "N/A" );
        }
        
        //display email
        else
        {
            email.setText(person.getEmail());
        }
        
    }
    
    //method to finish the activity once the user touches the screen
    @Override
    public boolean onTouchEvent( MotionEvent mEvent ){
        finish();
        return true;
    }

}
