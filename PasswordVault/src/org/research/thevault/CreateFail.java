package org.research.thevault;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;

public class CreateFail extends Activity {

	@Override
	public void onCreate( Bundle savedInstanceState ){
		super.onCreate( savedInstanceState );
		setContentView( R.layout.create_fail );
	}
	
	@Override
	public boolean onTouchEvent( MotionEvent mEvent ){
		finish();
		return true;
	}
}
