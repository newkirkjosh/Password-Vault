package org.research.thevault;

public class Locations {

	private String latitude;
	private String longitude;
	private String address;
	private int visitNum;
	
	Locations( String latitude, String longitude, String address, int visitNum )
	{
		this.latitude = latitude;
		this.longitude = longitude;
		this.address = address;
		this.visitNum = visitNum;
	}
	
	public String getLatitude()
	{
		return latitude;
	}
	
	public void setLatitude( String latitude )
	{
		this.latitude = latitude;
	}
	
	public String getLongitude()
	{
		return longitude;
	}
	
	public void setLongitude( String longitude )
	{
		this.longitude = longitude;
	}
	
	public String getAddress()
	{
		return address;
	}
	
	public void setAddress( String address )
	{
		this.address = address;
	}
	
	public int getVisitNum()
	{
		return visitNum;
	}
	
	public void setVisitNum( int visitNum )
	{
		this.visitNum = visitNum;
	}
}
