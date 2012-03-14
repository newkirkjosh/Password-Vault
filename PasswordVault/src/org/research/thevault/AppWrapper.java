package org.research.thevault;

import android.content.Context;
import android.content.pm.ApplicationInfo;

//wrapper for apps to be able to be put into a List
public class AppWrapper implements Comparable<AppWrapper>{

    private Context c;
    private ApplicationInfo ai;
    
    AppWrapper( Context c, ApplicationInfo ai ){
        this.c = c;
        this.ai = ai;
    }
    
    public ApplicationInfo getAI() {
        return ai;
    }

	//implement compareTo 
    public int compareTo(AppWrapper another) {
        // TODO Auto-generated method stub
        String aiName = (String)ai.loadLabel(c.getPackageManager());
        String anotherName = (String)another.getAI().loadLabel( c.getPackageManager());
        return aiName.compareToIgnoreCase( anotherName );
    }
}
