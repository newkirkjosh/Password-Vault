package org.research.thevault;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;

//method to alert the user they need to create a password
public class CreateFail extends Activity {

	@Override
	public void onCreate( Bundle savedInstanceState ){
		super.onCreate( savedInstanceState );
		setContentView( R.layout.create_fail );
	}

	//method to clear dialog on user touch	
	@Override
	public boolean onTouchEvent( MotionEvent mEvent ){
		finish();
		return true;
	}
}
