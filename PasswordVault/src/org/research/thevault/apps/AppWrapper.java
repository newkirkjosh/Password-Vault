package org.research.thevault.apps;

import android.content.Context;
import android.content.pm.ApplicationInfo;

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

    public int compareTo(AppWrapper another) {
        // TODO Auto-generated method stub
        String aiName = (String)ai.loadLabel(c.getPackageManager());
        String anotherName = (String)another.getAI().loadLabel( c.getPackageManager());
        return aiName.compareToIgnoreCase( anotherName );
    }
}
