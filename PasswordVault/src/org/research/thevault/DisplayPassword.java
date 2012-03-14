package org.research.thevault;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.TextView;

//activity to display the password a user entered
//runs when user clicks Display Password on login screen
public class DisplayPassword extends Activity{
	@Override
	protected void onCreate( Bundle savedInstanceState ){
		super.onCreate( savedInstanceState );
		setContentView( R.layout.display_password );
		
		String password = getIntent().getStringExtra( "PASSWORD" );
		
		TextView textBox = (TextView) findViewById( R.id.display_password_page );

		textBox.setText( "Password: " + password );
	}
	
	//method to finish the activity once the user touches the screen
	@Override
	public boolean onTouchEvent( MotionEvent mEvent ){
		finish();
		return true;
	}
}