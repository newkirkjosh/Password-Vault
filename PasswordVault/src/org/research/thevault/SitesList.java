package org.research.thevault;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class SitesList extends ListActivity implements Constants{
	
	private SiteTable st;
	private SimpleCursorAdapter adapter;
	private SQLiteDatabase db;
	private Cursor cursor;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sites_list);
        
        registerForContextMenu(getListView()); 
        
        st = new SiteTable( this );
        cursor = getSites();
        showSites( cursor );
        
        LinearLayout l = (LinearLayout)findViewById( R.id.site_container );
        registerForContextMenu( l );
	}
	
	@Override
	public void onDestroy(){
	    if( cursor != null )
	        cursor.close();
	    if( db != null )
	        db.close();
	    super.onDestroy();
	}
	
	@Override
    public boolean onCreateOptionsMenu( Menu menu ){
    	super.onCreateOptionsMenu( menu );
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate( R.menu.menu, menu);
    	return true;
    }
	
    @Override
    // This method is when the long click is pressed, determines which action to take based on the MenuItem selected
    public boolean onOptionsItemSelected( MenuItem item ){
    	switch( item.getItemId() ){
    	case R.id.add:
    		addSite();
    		return true;
    	case R.id.gen_pwd:
            genPass();
            return true;
    	case R.id.location:
    		getGeneratedLocation();
    		return true;
    	}
    	return false;
    }
    
    public Cursor getSites(){
    	db = st.getReadableDatabase();
		cursor = db.rawQuery( "SELECT * FROM " + SITE_TABLE_NAME, null);
		startManagingCursor( cursor );
		return cursor;
    }
    
    public void showSites( Cursor cursor){
    	
    	String[] from = { URL };
    	int[] to = { R.id.url_item };
    	adapter = new SimpleCursorAdapter( this, R.layout.site_item, cursor, from, to);
		setListAdapter( adapter );
    }
    
    @Override
    protected void onListItemClick( ListView l, View v, int position, long id ){
    	super.onListItemClick(l, v, position, id);
    	cursor.moveToPosition( position );
    	
    	showDetails();
    }
    
    @Override 
    public boolean onContextItemSelected(MenuItem item) { 
    	if( item.getItemId() == 0 || item.getItemId() == 1 ){
    		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
    		cursor.moveToPosition( info.position );
    	}
    	switch( item.getItemId() ){
    		case 0:
		    	int id = cursor.getInt( cursor.getColumnIndex( _ID ));
		    	db = st.getWritableDatabase();
		    	db.delete(SITE_TABLE_NAME, _ID + "=" + id, null);
		    	cursor.requery();
		    	break;
    		case 1:
    			showDetails();
    			break;
    		case 2:
    			addSite();
    			break;
    		case 3:
    		    genPass();
    		    break;
    		case 4:
    			getGeneratedLocation();
    			break;
    	}
        return true; 
    } 
    
    @Override 
    // Long click menu options
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) { 
    	if(v.getId() != R.id.site_container ){
    		menu.add( 1, 0, Menu.NONE, "Delete" );
    		menu.add( 1, 1, Menu.NONE,"View Details" );
    	}
    	else{
    		menu.add( 0, 2, Menu.NONE, "Add Site" );
    		menu.add( 0, 3, Menu.NONE, "Generate Password" );
    		menu.add( 0, 4, Menu.NONE, "View Current Location");
    	}
    }
    
    // Details of a listed site
    private void showDetails(){
    	String url = cursor.getString( cursor.getColumnIndex( URL));
    	String uName = cursor.getString( cursor.getColumnIndex( UNAME ));
    	String pWord = cursor.getString( cursor.getColumnIndex( PWORD ));
    	Intent details = new Intent( this, Details.class );
    	details.putExtra( URL,  url );
    	details.putExtra( UNAME, uName );
    	details.putExtra( PWORD, pWord );
    	startActivity( details );
    }
    
    private void addSite(){
    	Intent addSite = new Intent( this, AddPage.class );
		startActivity( addSite );
    }
    
    private void genPass() {
        Intent genPass = new Intent(this, GeneratePass.class);
        startActivity( genPass );
    }
    
    // Shows user a random location on the map while telling the user that it is getting user's current location
    // While this is happening we would like to get the current location and make note of it in a file or log
    private void getGeneratedLocation(){
    	String uri = "geo:" + genLatitude() + "," + genLongitude();
    	Intent showLocations = new Intent( android.content.Intent.ACTION_VIEW, Uri.parse(uri));
    	showLocations.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
    	startActivity( showLocations );
    }
    
    // Represents N and S directions so bounds go from -90 to 90 degrees
    private float genLatitude(){ 
    	return (float)(Math.random() * 180) - 90;
    }
    
    // Represents E and W directions so bounds go from -180 to 180 degrees
    private float genLongitude(){
    	return (float)(Math.random() * 360) - 180;
    }
}
