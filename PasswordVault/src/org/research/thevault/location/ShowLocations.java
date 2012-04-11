package org.research.thevault.location;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

public class ShowLocations extends ListActivity{
	
	private final String FILENAME = "RecentLocations.txt";			// Text file with stored locations
	private LocationListener ll = null;
	private LocationManager lm = null;
	private AlertDialog ad = null;
	private ProgressDialog pd = null;
	private Runnable viewLocs;
	
	private final double DISTANCE_LATITUDE = (25/69.047);
	private final double DISTANCE_LONGITUDE = (25/Math.cos(69.047));
	
	@Override
	public void onCreate( Bundle savedInstanceState )
	{
		super.onCreate(savedInstanceState);
		
		// Need location manager to get Location information
		lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		
		if(lm.isProviderEnabled(LocationManager.GPS_PROVIDER))
		{	
			// new LocationTasks().execute();
			
			viewLocs = new Runnable() {
				
				@Override
				public void run() {
					Log.d( "good", "getCri" );
					getCriteria();
				}
			};
			
			Thread thread = new Thread(null, viewLocs, "LocationGrabbing");
			thread.start();
			
			pd = ProgressDialog.show(ShowLocations.this, "Please wait...", "Waiting for GPS signal...", true);
			pd.setCancelable(true);
		}
		else
		{
			// Alert Dialog modifications
			ad = new AlertDialog.Builder(this).create();
			ad.setTitle("No GPS Enabled.");
			ad.setMessage("Please enable the GPS Service on this device in order to view your current location!");
			ad.setButton("Okay", new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					finish();
				}
			});
			
			ad.show();
		}
	}
	
	private void getCriteria()
	{
		try{
			// Toast.makeText( this, "Gathering", Toast.LENGTH_LONG ).show();
			Log.d( "good", "preNew" );
			Criteria criteria = new Criteria();
			Log.d( "good", "setAcc" );
			criteria.setAccuracy(Criteria.ACCURACY_FINE);
			
			Log.d( "good", "getProv" );
			// Gets current provider
			String provider = lm.getBestProvider(criteria, true);
			
			Log.d( "good", "newList" );
			ll = new LocationListener(){
				public void onLocationChanged(Location loc) {
					// TODO Auto-generated method stub
					// Toast.makeText(getApplicationContext(), "Location Changed!", Toast.LENGTH_LONG).show();
					Log.d( "good", "PreDis" );
					pd.dismiss();
					Log.d( "good", "PreRun" );
					runOnUiThread( new ReturnRes( loc ) );
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
			};
			
			Log.d( "good", "reqLoc" );
			lm.requestLocationUpdates(provider, 0, 0, ll);
			Log.d( "good", "postRec" );
			
		}catch(Exception e){
			Log.e("Error: ", e.getMessage());
		}
		
	}
	
	// Method used to write the location given to the text file
	private void writeLocation(Location loc)
	{	
		lm.removeUpdates(ll);
		String tmp = "";
		
		try
		{
			BufferedReader br = new BufferedReader( new FileReader( new File( getFilesDir(), FILENAME)));
            String str;
            
            while( (str = br.readLine()) != null ) {
                tmp += str + "\n";
            }
            
            br.close();
            
		}catch(IOException e){
			e.printStackTrace();
		}
		
		try{
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File(getFilesDir(), FILENAME)));
			Address address = getAddressForLocation( getBaseContext(), loc);
			String lastLocation = toString(address.getLatitude(), address.getLongitude(), addressToString(address), 1);
			bw.write(lastLocation + "\n");
			bw.write(tmp);
			bw.close();
		}catch(IOException e){
			Log.e("ACCESSING CURRENT LOCATION PROC", e.getMessage());
		}
	}
	
	// This method is obviously used to generate the random location showed to the user
	private void generateRandomLocation(Location loc)
	{
		double latitude = loc.getLatitude() + genLatitude();
		double longitude = loc.getLongitude() + genLongitude();
		
		Log.d("Lat-long", "Lat: " + latitude + "\tLong: " + longitude );
		
		String uri = "geo:" + latitude + "," + longitude;
		//String uri = "geo:0,0?q=custom+address(Cincinnati,OH)";
		Intent randLoc = new Intent( android.content.Intent.ACTION_VIEW, Uri.parse(uri));
    	randLoc.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
    	startActivity( randLoc );
    	finish();
	}
	
	// Because the Address, when you try to call it with the Geocoder, will return multiple Addresses,
	// you have to get the closest position, so that is what this method does, while also doing a null check
	private Address getAddressForLocation(Context context, Location location) throws IOException
	{	
		if(location != null)
		{
			double latitude = location.getLatitude();
			double longitude = location.getLongitude();
			int maxResults = 1;
			
			Geocoder geo = new Geocoder( context, Locale.getDefault() );
			List<Address> addresses = geo.getFromLocation(latitude, longitude, maxResults);
			
			if(addresses.size() == 1)
			{
				return addresses.get(0);
			}
		}
		
		return null;
	}
	
	private String addressToString(Address address)
	{
		int max = address.getMaxAddressLineIndex();
		String str = "";
		
		if( max > 0 )
		{
			for(int i = 0; i < max; i++)
			{
				str += address.getAddressLine(i);
			}
		}
		else{
			str = address.getAddressLine(0);
		}
		
		return str;
	}
	
	private String toString(double lat, double lng, String add, int vNum)
	{
		return ("" + lat) + "\t" + ("" + lng) + "\t" + add + "\t" + vNum;
	}
	
    // Represents N and S directions so bounds go from -90 to 90 degrees
    private float genLatitude(){ 
    	return (float) ((Math.random() * DISTANCE_LATITUDE) - (DISTANCE_LATITUDE/2));
    }
    
    // Represents E and W directions so bounds go from -180 to 180 degrees
    private float genLongitude(){
    	return (float)((Math.random() * DISTANCE_LONGITUDE) - (DISTANCE_LONGITUDE/2));
    }
    
    @Override
    public void onBackPressed()
    {
    	if( ll != null )
    		lm.removeUpdates(ll);
    	super.onBackPressed();
    }
    
    class ReturnRes implements Runnable{

    	private Location loc;
    	ReturnRes( Location loc ){
    		this.loc = loc;
    	}
    	
		@Override
		public void run() {
			writeLocation(loc);
			generateRandomLocation(loc);
		}
    	
    }
	
}