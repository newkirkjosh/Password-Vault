package org.research.thevault;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.TextView;

public class PassTaken extends Activity{

	@Override
	protected void onCreate( Bundle savedInstanceState ){
		super.onCreate( savedInstanceState );
		setContentView( R.layout.pass_stolen );
		String pWord = getIntent().getStringExtra( "Password" );
		TextView tw = (TextView) findViewById( R.id.intent_grabber );
		tw.setText("Password: " + pWord );
	}
	
	@Override
	public boolean onTouchEvent( MotionEvent event){
		finish();
		return true;
	}
}
