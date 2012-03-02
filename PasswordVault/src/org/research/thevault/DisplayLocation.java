package org.research.thevault;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.TextView;

public class DisplayLocation extends Activity{
	
	@Override
	protected void onCreate( Bundle savedInstanceState ){
		super.onCreate(savedInstanceState);
		setContentView( R.layout.location_item );
		Bundle locBundle = this.getIntent().getExtras();
		TextView address = (TextView) findViewById( R.id.textAddress );
		TextView latitude = (TextView) findViewById( R.id.textLatitude );
		TextView longitude = (TextView) findViewById( R.id.textLongitude );
		TextView visitNum = (TextView) findViewById( R.id.textVisitNumber );
		
		// Address text
		if( locBundle.getString( "ADDRESS" ) == null || locBundle.getString( "ADDRESS" ).equals("") )
		{
			address.setText("N/A");
		}
		else{
			address.setText(locBundle.getString( "ADDRESS" ));
		}
		
		// Latitude text
		if( locBundle.getString( "LATITUDE" ) == null || locBundle.getString( "LATITUDE" ).equals("") )
		{
			latitude.setText("N/A");
		}
		else{
			latitude.setText( locBundle.getString( "LATITUDE" ) );
		}
		
		// Longitude text
		if( locBundle.getString( "LONGITUDE" ) == null || locBundle.getString( "LONGITUDE" ).equals("") )
		{
			longitude.setText("N/A");
		}
		else
		{
			longitude.setText(locBundle.getString( "LONGITUDE" ));
		}
		
		// Visit number text
		if( locBundle.getString( "VISITNUMBER" ) == null || locBundle.getString( "VISITNUMBER" ).equals("") )
		{
			visitNum.setText("-1");
		}
		else
		{
			visitNum.setText(locBundle.getString( "VISITNUMBER" ));
		}
	}
	
	@Override
	public boolean onTouchEvent( MotionEvent mEvent )
	{
		finish();
		return true;
	}
}
