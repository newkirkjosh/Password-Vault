package org.research.thevault;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;

//activity for the about this app dialog
public class About extends Activity{
	@Override
	protected void onCreate( Bundle savedInstanceState ){
		super.onCreate( savedInstanceState );
		setContentView( R.layout.about );
	}
	
	//get touch of screen to kill the activity
	@Override
	public boolean onTouchEvent( MotionEvent mEvent ){
		finish();
		return true;
	}
}
