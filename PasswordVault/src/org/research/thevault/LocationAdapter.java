package org.research.thevault;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LocationAdapter extends ArrayAdapter<Locations>{
	
	int resource;
	String response;
	Context context;
	
	public LocationAdapter(Context context, int resource, List<Locations> items)
	{
		super(context, resource, items);
		this.resource = resource;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		LinearLayout locationView;
		Locations locations = getItem(position);
		
		// Inflate the view
		if(convertView == null)
		{
			locationView = new LinearLayout(getContext());
			String inflater = Context.LAYOUT_INFLATER_SERVICE;
			LayoutInflater li;
			li = (LayoutInflater)getContext().getSystemService(inflater);
			li.inflate(resource, locationView, true);
		}
		else
		{
			locationView = (LinearLayout) convertView;
		}
		
		// Get the text boxes from the location_item.xml
		TextView address;
		
		return convertView;
	}

}
