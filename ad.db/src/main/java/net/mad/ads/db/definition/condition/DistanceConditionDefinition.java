package net.mad.ads.db.definition.condition;

import java.util.ArrayList;
import java.util.List;

import net.mad.ads.db.definition.ConditionDefinition;
import net.mad.ads.db.enums.Country;
import net.mad.ads.db.utils.geo.GeoLocation;

/**
 * Steuerung für welche Entfernung ein Banner angezeigt werden soll
 * 
 * @author tmarx
 *
 */
public class DistanceConditionDefinition implements ConditionDefinition {

	/*
	 * Der Mittelpunkt zur Berechnung der Entfernung
	 */
	private GeoLocation geoLocation = null;
	
	/*
	 * Die maximale Entfernung in der das Banner angezeigt werden soll
	 */
	private int geoRadius = -1;
	
	public DistanceConditionDefinition () {
		
	}
	
	
	public GeoLocation getGeoLocation() {
		return this.geoLocation;
	}

	
	public void setGeoLocation(GeoLocation location) {
		this.geoLocation = location;
	}
	
	public final int getGeoRadius() {
		return geoRadius;
	}
	
	public final void setGeoRadius(int geoRadius) {
		this.geoRadius = geoRadius;
	}
}
