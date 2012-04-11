package org.research.thevault.contacts;

import java.io.Serializable;

public class Persons implements Serializable{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String name;
    private String number;
    private byte[] thumb;
    private int photo;
    private String email;
    
    Persons( String name, byte[] thumb, int photo ){
        this.name = name;
        this.thumb = thumb;
        this.photo = photo;
        number = "";
        email = "";
    }
    
    public String getName() {
        return name;
    }
    
    public void setNumber( String number ) {
        this.number = number;
    }
    
    public String getNumber() {
        return number;
    }
    
    public int getPhotoId() {
        return photo;
    }
    
    public byte[] getThumbData() {
        return thumb;
    }
    
    public void setEmail( String email ) {
        this.email = email;
    }
    
    public String getEmail(){
    	return email;
    }
}
