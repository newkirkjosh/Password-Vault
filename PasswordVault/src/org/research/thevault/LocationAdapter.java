package org.research.thevault;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class LocationAdapter extends ArrayAdapter<Locations>{
	
	private ArrayList<Locations> items;		// List of locations
	
	public LocationAdapter(Context context, int resource, ArrayList<Locations> items)
	{
		super(context, resource, items);
		this.items = items;
	}
	
	@Override
	// Used to populate the view within a single textView
	public View getView(int position, View convertView, ViewGroup parent)
	{
		View v = convertView;
		if(v == null)
		{
			String inflater = Context.LAYOUT_INFLATER_SERVICE;
			LayoutInflater li = (LayoutInflater) getContext().getSystemService(inflater);
			v = li.inflate(R.layout.display_location, null);
		}
		
		Locations loc = items.get(position);
		
		if( loc != null )
		{
			TextView lat = (TextView) v.findViewById(R.id.textLatitude);
			TextView lng = (TextView) v.findViewById(R.id.textLongitude);
			TextView add = (TextView) v.findViewById(R.id.textAddress);
			TextView vn = (TextView) v.findViewById(R.id.textVisitNumber);
			
			if(lat != null){
				lat.setText("Latitude: " + loc.getLatitude() );
			}
			if( lng != null ){
				lng.setText("Longitude: " + loc.getLongitude() );
			}
			if( add != null){
				add.setText("Address: " + loc.getAddress() );
			}
			if( vn != null){
				vn.setText("Visit Number: " + loc.getVisitNum() );
			}
		}
		
		return v;
	}

}
