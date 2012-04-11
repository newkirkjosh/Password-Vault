package org.research.thevault;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.TextView;
import android.widget.ImageView;

public class DisplayPerson extends Activity{
    @Override
    protected void onCreate( Bundle savedInstanceState ){
        super.onCreate( savedInstanceState );
        setContentView( R.layout.display_contact );
        Persons person = (Persons) this.getIntent().getSerializableExtra( "PERSON" );
        TextView name = (TextView) findViewById( R.id.textName );
        TextView email = (TextView) findViewById( R.id.textEmail );
        TextView phone = (TextView) findViewById( R.id.textPhone );
        ImageView photo = (ImageView) findViewById( R.id.ContactBadge );
        name.setText( person.getName() );
        
        if( person.getThumbData() != null ) {
            Bitmap map = BitmapFactory.decodeByteArray(person.getThumbData(), 0, person.getThumbData().length );
            photo.setImageBitmap( map );
        }
        else {
            photo.setImageResource( R.drawable.droid );
        }
        
        if( person.getNumber() == null || person.getNumber().equals( "" ) )
        {
        	phone.setText("N/A");
        }
        else
        {
        	phone.setText(person.getNumber());
        }
        
        if( person.getEmail() == null || person.getEmail().equals( "" ) )
        {
            email.setText( "N/A" );
        }
        else
        {
            email.setText(person.getEmail());
        }
        
    }
    
    @Override
    public boolean onTouchEvent( MotionEvent mEvent ){
        finish();
        return true;
    }

}
