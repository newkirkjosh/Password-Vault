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

public class ShowApps extends ListActivity implements ListAdapter{
    
    private List<AppWrapper> ai;
    private LayoutInflater inflate;
    private PackageManager pm;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_apps);
        
        pm = getPackageManager();
        List<ApplicationInfo> loc = new ArrayList<ApplicationInfo>();
        for( ApplicationInfo a : pm.getInstalledApplications(0) ) {
            if( ((String) a.loadLabel( getPackageManager())).matches( "^[^\\.]+$" ) ) {
                loc.add(a);
            }
        }
        ai = new ArrayList<AppWrapper>();
        for( ApplicationInfo a : loc ) {
            ai.add( new AppWrapper( this, a ));
        }
        Collections.sort( ai );
        inflate = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        setListAdapter( this );
    }
    

    public int getCount() {
        // TODO Auto-generated method stub
            return ai.size();
    }

    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
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
            convertView = inflate.inflate(R.layout.app_item, null, true );
        AppWrapper locAI = ai.get( position );
        
        ((ImageView) convertView.findViewById(R.id.app_icon)).setImageDrawable( locAI.getAI().loadIcon( pm ));
        ((TextView) convertView.findViewById(R.id.app_item)).setText( locAI.getAI().loadLabel( pm ));
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
        return ai.isEmpty();
    }

    public void registerDataSetObserver(DataSetObserver observer) {
        // TODO Auto-generated method stub
        
    }

    public void unregisterDataSetObserver(DataSetObserver observer) {
        // TODO Auto-generated method stub
        
    }

    public boolean areAllItemsEnabled() {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean isEnabled(int position) {
        // TODO Auto-generated method stub
        return false;
    }
}
