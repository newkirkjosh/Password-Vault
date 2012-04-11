package org.research.thevault;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;

public class BadPageDetails extends Activity{
	
	@Override
	protected void onCreate( Bundle savedInstanceState ){
		super.onCreate( savedInstanceState );
		setContentView( R.layout.bad_page_screen );
	}
	
	@Override
	public boolean onTouchEvent( MotionEvent mEvent ){
		finish();
		return true;
	}
}
