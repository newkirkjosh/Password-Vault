package org.research.thevault;

import java.io.Serializable;
import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.CommonDataKinds.*;
import android.provider.ContactsContract.*;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ShowContacts extends ListActivity implements ListAdapter, OnItemClickListener{
    
    private LayoutInflater inflate;
    Persons[] person;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.show_contacts );
        
        ContentResolver cr = getContentResolver();
        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, new String[] { Contacts.DISPLAY_NAME, BaseColumns._ID }, null, null, null );
        person = new Persons[ cursor.getCount() ]; 
        for( int i = 0; i < cursor.getCount(); i++ ) {
            cursor.moveToNext();
            
            long contactId = cursor.getLong( cursor.getColumnIndex( BaseColumns._ID ));
            Uri contactUri = ContentUris.withAppendedId(Contacts.CONTENT_URI, contactId);
            Uri photoUri = Uri.withAppendedPath(contactUri, Contacts.Photo.CONTENT_DIRECTORY);
            Cursor cursor3 = getContentResolver().query(photoUri, new String[] {Contacts.Photo.DATA15, Contacts.Photo.DATA14}, null, null, null);
            
            if( cursor3.moveToNext() ) {
                person[i] = new Persons( cursor.getString( cursor.getColumnIndex(  Contacts.DISPLAY_NAME )), cursor3.getBlob( cursor3.getColumnIndex( Contacts.Photo.DATA15 )), cursor3.getInt( cursor3.getColumnIndex( Contacts.Photo.DATA14 )));
            }
            else {
                person[i] = new Persons( cursor.getString( cursor.getColumnIndex(  Contacts.DISPLAY_NAME )), null, 0 );
            }
            cursor3.close();
            
            //getting Email
            cursor3 = cr.query(Email.CONTENT_URI, new String[] {Email.DATA}, Data.CONTACT_ID + "=?" + 
                    " AND " + Email.MIMETYPE + "='" + Email.CONTENT_ITEM_TYPE + "'", new String[] { String.valueOf(contactId) }, null );
            
            if( cursor3.moveToNext() ) {
                Log.d("DATA",""+ cursor3.getColumnIndex( Email.DATA )) ;
               person[i].setEmail( cursor3.getString( cursor3.getColumnIndex( Email.DATA )));
            }
            cursor3.close();
            
            //getting Phone Number
            cursor3 = cr.query(Data.CONTENT_URI, new String[] { Phone.NUMBER }, Data.CONTACT_ID + "=?" + 
                    " AND " + Data.MIMETYPE + "='" + Phone.CONTENT_ITEM_TYPE + "'", new String[] { String.valueOf(contactId) }, null );
            
            if( cursor3.moveToNext() ) {
                person[i].setNumber( cursor3.getString( cursor3.getColumnIndex( Phone.NUMBER )));
            }
            cursor3.close();
        }
        cursor.close();
        inflate = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        setListAdapter( this );
        ListView lv = getListView();
        lv.setOnItemClickListener( this );
    }
    
    public int getCount() {
        // TODO Auto-generated method stub
        
        return person.length;
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
            convertView = inflate.inflate(R.layout.contact_item, null, true );
        if( person[position].getThumbData() != null ) {
            Bitmap map = BitmapFactory.decodeByteArray(person[position].getThumbData(), 0, person[position].getThumbData().length );
            ((ImageView) convertView.findViewById( R.id.contact_icon)).setImageBitmap( map );
        }
        else {
            ((ImageView) convertView.findViewById( R.id.contact_icon)).setImageResource( R.drawable.droid );
        }
        ((TextView) convertView.findViewById(R.id.contact_item)).setText( person[position].getName() );
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
        return person.length == 0;
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
        return true;
    }

    public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
        // TODO Auto-generated method stub
        Intent personData = new Intent( this, DisplayPerson.class );
        personData.putExtra("PERSON", (Serializable)person[position] );
        startActivity( personData );
    }
}
