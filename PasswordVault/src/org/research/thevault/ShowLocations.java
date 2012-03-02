package org.research.thevault;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ShowLocations extends ListActivity implements ListAdapter, OnItemClickListener{
	
	private LayoutInflater inflate;
	Locations[] locations;
	
	@Override
	public void onCreate( Bundle savedInstanceState )
	{
		super.onCreate(savedInstanceState);
		setContentView( R.layout.show_locations );
		
		inflate = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		setListAdapter( this );
		ListView lv = getListView();
		lv.setOnItemClickListener( this );
	}
	
	public int getCount() {
		// TODO Auto-generated method stub
		return locations.length;
	}
	
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if( convertView == null )
		{
			convertView = inflate.inflate(R.layout.location_item, null, true );
		}
		
		((TextView) convertView.findViewById(R.id.location_item)).setText( locations[position].getAddress() );
		
		return convertView;
	}
	
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 1;
	}
	
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return locations.length == 0;
	}
	
	public void registerDataSetObserver(DataSetObserver observer) {
		// TODO Auto-generated method stub
		
	}
	
	public void unregisterDataSetObserver(DataSetObserver observer) {
		// TODO Auto-generated method stub
		
	}
	
	public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
		// TODO Auto-generated method stub
		Intent locationData = new Intent( this, DisplayLocation.class );
		locationData.putExtra("ADDRESS", locations[position].getAddress());
		locationData.putExtra("LATITUDE", locations[position].getLatitude());
		locationData.putExtra("LONGITUDE", locations[position].getLongitude());
		locationData.putExtra("VISITNUMBER", locations[position].getVisitNum());
		startActivity( locationData );
	}
	
	public boolean areAllItemsEnabled() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public boolean isEnabled(int position) {
		// TODO Auto-generated method stub
		return true;
	}
}
