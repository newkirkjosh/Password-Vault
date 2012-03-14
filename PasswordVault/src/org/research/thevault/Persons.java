package org.research.thevault;

import java.io.Serializable;

//person object
public class Persons implements Serializable{
    
    
    //create the variables
    private static final long serialVersionUID = 1L;
    private String name;
    private String number;
    private byte[] thumb;
    private int photo;
    private String email;
    
    //constructor
    public Persons( String name, byte[] thumb, int photo ){
        this.name = name;
        this.thumb = thumb;
        this.photo = photo;
        number = "";
        email = "";
    }
    
    //getters and setters
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
