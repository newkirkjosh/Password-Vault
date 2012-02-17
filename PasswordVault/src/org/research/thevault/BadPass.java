package org.research.thevault;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.TextView;

public class BadPass extends Activity{
	
	@Override
	protected void onCreate( Bundle savedInstanceState ){
		super.onCreate( savedInstanceState );
		setContentView( R.layout.bad_pass_screen );
		
		String pWord = getIntent().getStringExtra( "Password" );
		
		TextView textBox = (TextView) findViewById( R.id.bad_pass );
		
		textBox.setText("Password: " + pWord );
		
	}
	
	@Override
	public boolean onTouchEvent( MotionEvent mEvent ){
		finish();
		return true;
	}
}
