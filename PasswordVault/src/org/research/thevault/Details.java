package org.research.thevault;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.TextView;

//activity to show the details of the site a user clicks
public class Details extends Activity implements Constants{
	@Override
	protected void onCreate( Bundle savedInstanceState ){
		super.onCreate( savedInstanceState );
		setContentView( R.layout.details );
		
		String url = getIntent().getStringExtra( URL );
		String uName = getIntent().getStringExtra( UNAME );
		String pWord = getIntent().getStringExtra( PWORD );
		
		TextView textBox = (TextView) findViewById( R.id.site_details );
		
		textBox.setText("URL: " + url + "\nUsername: " + uName + "\nPassword: " + pWord );
		
	}
	
	//method to finish the activity once the user touches the screen
	@Override
	public boolean onTouchEvent( MotionEvent me ){
		finish();
		return true;
	}
}
