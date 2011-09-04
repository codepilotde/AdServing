package net.mad.ads.services.geo;

public class Location {
	
	public static final Location UNKNOWN = new Location("", "", "");
	
	private String regionName = "";
	private String country = "";
	private String city = "";
	
	private String latitude = "";
	private String longitude = "";
	

	public Location (String country, String regionName, String city) {
		this.country = country;
		this.regionName = regionName;
		this.city = city;
	}
	
	public Location (String country, String regionName, String city, String latitude, String lonitude) {
		this(country, regionName, city);
		this.latitude = latitude;
		this.longitude = lonitude;
	}
	
	
	
	
	public String getLatitude() {
		return latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public String getCountry() {
		return country;
	}

	public String getRegionName() {
		return regionName;
	}

	public String getCity() {
		return city;
	}
	
	
	
}
