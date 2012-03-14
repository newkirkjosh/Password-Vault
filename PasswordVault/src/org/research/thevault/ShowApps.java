package org.research.thevault;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.ListActivity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

//activity to display all apps installed on device
public class ShowApps extends ListActivity implements ListAdapter{
    
    //create the variables
    private List<AppWrapper> ai;
    private LayoutInflater inflate;
    private PackageManager pm;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_apps);
    	
    	//get the package manager and create list
        pm = getPackageManager();
        List<ApplicationInfo> loc = new ArrayList<ApplicationInfo>();
        
        //add all apps to the list
        for( ApplicationInfo a : pm.getInstalledApplications(0) ) {
            if( ((String) a.loadLabel( getPackageManager())).matches( "^[^\\.]+$" ) ) {
                loc.add(a);
            }
        }
        ai = new ArrayList<AppWrapper>();
        for( ApplicationInfo a : loc ) {
            ai.add( new AppWrapper( this, a ));
        }
        
        //sort the apps alphabetically and create the listview
        Collections.sort( ai );
        inflate = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        setListAdapter( this );
    }
    
	//returns the amount of apps found
    public int getCount() {
        // TODO Auto-generated method stub
            return ai.size();
    }

	//unneeded method that is required for implementation
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

	//unneeded method that is required for implementation
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

	//unneeded method that is required for implementation
    public int getItemViewType(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

	//creates and/or returns the view with current app at the respective location
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        if( convertView == null )
            convertView = inflate.inflate(R.layout.app_item, null, true );
        AppWrapper locAI = ai.get( position );
        
        ((ImageView) convertView.findViewById(R.id.app_icon)).setImageDrawable( locAI.getAI().loadIcon( pm ));
        ((TextView) convertView.findViewById(R.id.app_item)).setText( locAI.getAI().loadLabel( pm ));
        return convertView;
    }

	//unneeded method that is required for implementation
    public int getViewTypeCount() {
        // TODO Auto-generated method stub
        return 1;
    }

	//unneeded method that is required for implementation
    public boolean hasStableIds() {
        // TODO Auto-generated method stub
        return false;
    }

	//returns if no apps installed
    public boolean isEmpty() {
        // TODO Auto-generated method stub
        return ai.isEmpty();
    }

	//unneeded method that is required for implementation
    public void registerDataSetObserver(DataSetObserver observer) {
        // TODO Auto-generated method stub
        
    }

	//unneeded method that is required for implementation
    public void unregisterDataSetObserver(DataSetObserver observer) {
        // TODO Auto-generated method stub
        
    }

	//unneeded method that is required for implementation
    public boolean areAllItemsEnabled() {
        // TODO Auto-generated method stub
        return false;
    }

	//unneeded method that is required for implementation
    public boolean isEnabled(int position) {
        // TODO Auto-generated method stub
        return false;
    }
}
