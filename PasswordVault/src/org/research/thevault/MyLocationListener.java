package org.research.thevault;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

public class MyLocationListener implements LocationListener {

	public void onLocationChanged(Location loc) {
		// TODO Auto-generated method stub
		
		if( loc != null){
			String text = "My current location is: " + 
					"Latitude = " + loc.getLatitude() + 
						"Longitude = " + loc.getLongitude();
			Log.d("LocChanged", text );
		}
	}

	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

}
