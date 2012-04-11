package org.research.thevault;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;

public class DisplayLocation extends ListActivity{
	
	private ProgressDialog myProgressDialog = null;			// Used for dialogbox on listload
	private ArrayList<Locations> myLocations = null;		// list of locations
	private LocationAdapter myAdapter = null;				// Needed an adapter for the locations
	private Runnable viewLocations;							// Used for threading
	private final String FILENAME = "RecentLocations.txt";	// File to be read from
	
	@Override
	protected void onCreate( Bundle savedInstanceState ){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_locations);
		
		// Declare new array
		myLocations = new ArrayList<Locations>();
		
		// Set the adapter to populate with 
		this.myAdapter = new LocationAdapter(this, R.layout.location_item, myLocations);
		setListAdapter(this.myAdapter);
		
		// Used for threading
		viewLocations = new Runnable(){
			public void run()
			{
				getLocations();
			}
		};
		// Created thread
		// Thread thread = new Thread(null, viewLocations, "MagentoBackground");
		// thread.start();
		
		// Displays the progress dialog when loading the list
		// myProgressDialog = ProgressDialog.show(DisplayLocation.this, "Please wait...", "Retrieving data...", true);
	}
	
	// Method that retrieves all of the stored locations from the text file
	private void getLocations()
	{
		myLocations = new ArrayList<Locations>();
		String[] fields;
		String line;
		Locations temp;
		
		try{
			BufferedReader br = new BufferedReader(new FileReader(new File(getFilesDir(), FILENAME)));
			while( (line = br.readLine()) != null )
			{
				// fields are in order of latitude, longitude, address, visitNumber
				fields = line.split("\t");
				temp = new Locations( fields[0], fields[1], fields[2], Integer.parseInt(fields[3]) );
				myLocations.add(temp);
			}
			Log.i("Array", "" + myLocations.size());
			br.close();
		}catch(IOException e){
			Log.e("BACKGROUND PROC", e.getMessage());
		}
		
		runOnUiThread(returnRes);
	}
	
	// Used for threading and letting the adapter know if the data has changed
	private Runnable returnRes = new Runnable(){
		
		public void run()
		{
			if( myLocations != null && myLocations.size() > 0 )
			{
				myAdapter.notifyDataSetChanged();
				for(int i = 0; i < myLocations.size(); i++)
				{
					myAdapter.add(myLocations.get(i));
				}
			}
			
			//myProgressDialog.dismiss();
			myAdapter.notifyDataSetChanged();
		}
	};
}
