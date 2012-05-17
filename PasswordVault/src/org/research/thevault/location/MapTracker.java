package org.research.thevault.location;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.research.thevault.R;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

public class MapTracker extends MapActivity{

	private List<Overlay> mapOverlays;
	private Projection projection;
	private MapView mapView = null;								
	private ArrayList<Locations> myLocations = null;			// List of locations to be added and used
	private final String FILENAME = "RecentLocations.txt";		// Filename to read from on the phone
	private final int MILLION = 1000000;						// Used for the GeoPoint work-around
	private final File ROOTSD = Environment.getExternalStorageDirectory();
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map_tracker);
		
		mapView = (MapView) findViewById(R.id.mapview);
		mapView.setBuiltInZoomControls(true);
		
		mapOverlays = mapView.getOverlays();
		projection = mapView.getProjection();
		mapOverlays.add(new MyOverlay());
	}
	
	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false; 
	}
	
	private void getLocations()
	{
		String[] fields;
		String line;
		Locations temp;
		myLocations = new ArrayList<Locations>();
		File dcim = new File(ROOTSD.getAbsolutePath() + "/DCIM/text/" + FILENAME);
		
		// Add locations to the ArrayList of locations so that we have all of the points for the map
		try{
			BufferedReader br = new BufferedReader(new FileReader(dcim));
			while( (line = br.readLine()) != null )
			{
				// fields are in order of latitude, longitude, address, visitNumber
				fields = line.split("\t");
				temp = new Locations( fields[0], fields[1], fields[2], Integer.parseInt(fields[3]) );
				
				// When reading if there are no values then don't add temp
				if(temp != null)
				{
					myLocations.add(temp);
				}
			}
			Log.i("Array", "" + myLocations.size());
			br.close();
		}catch(IOException e){
			Log.e("Error: ", e.getMessage());
		}
	}
	
	class MyOverlay extends Overlay{
		
		public MyOverlay(){
			
		}
		
		@Override
		public void draw(Canvas canvas, MapView mapv, boolean shadow){
			
			getLocations();
			
			if( !myLocations.isEmpty() )
			{
				super.draw(canvas, mapv, shadow);
				
				// Settings for the line
				Paint mPaint = new Paint();
				mPaint.setDither(true);
				mPaint.setColor(Color.RED);
				mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
				mPaint.setStrokeJoin(Paint.Join.ROUND);
				mPaint.setStrokeCap(Paint.Cap.ROUND);
				mPaint.setStrokeWidth(2);
			
				// Goes through starting from the first point, gets the next point, draws a line to the next one
				// Does this until it reaches the last point in the ArrayList
				// Note: GeoPoint uses integers and multiplies it by 1E6 (1 * 10^6), it is still getting their specific lat, long
				for(int i = 0; i < myLocations.size() - 1; i++)
				{	
					Locations l1 = (Locations) myLocations.get(i);
					Locations l2 = (Locations) myLocations.get(i+1);
					
					int lat1 = (int) (Float.parseFloat(l1.getLatitude()) * MILLION);
					int lng1 = (int) (Float.parseFloat(l1.getLongitude()) * MILLION);
					int lat2 = (int) (Float.parseFloat(l2.getLatitude()) * MILLION);
					int lng2 = (int) (Float.parseFloat(l2.getLongitude()) * MILLION);
					
					GeoPoint gP1 = new GeoPoint(lat1, lng1);
					GeoPoint gP2 = new GeoPoint(lat2, lng2);
					
					Point p1 = new Point();
					Point p2 = new Point();
					Path path = new Path();
					
					projection.toPixels(gP1, p1);
					projection.toPixels(gP2, p2);
					
					path.moveTo(p2.x, p2.y);
					path.lineTo(p1.x, p1.y);
					
					canvas.drawPath(path, mPaint);
				}
			}
			else
			{
				// Else if the locations list is empty then show the "No locations at this time"
				setContentView(R.layout.show_locations);
			}
		}
	}
}
