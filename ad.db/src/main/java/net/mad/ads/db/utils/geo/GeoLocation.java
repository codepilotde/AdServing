package net.mad.ads.db.utils.geo;


/**
 * Ein GeoPosition
 * 
 * @author tmarx
 *
 */
public class GeoLocation {

	private double latitude;
	private double longitude;
	
	public GeoLocation (double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	
	
}
