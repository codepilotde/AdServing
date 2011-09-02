package net.mad.ads.base.api.cluster.helper;

import de.marx.common.tools.Strings;

/**
 * Verteilung von User auf ein Cluster
 * @author tmarx
 *
 */
public enum ClusterAllocation {
	/*
	 * Automatische Verteilung
	 */
	AUTOMATIC ("AUTOMATIC"),
	/*
	 * Die Verteilung wird manuell durchgef�hrt
	 */
	MANUAL ("MANUAL");
		
	private String name;
	
	private ClusterAllocation (String name) {
		this.name = name;
	}
		
	public String getName () {
		return this.name;
	}
	public static ClusterAllocation forName (String name) {
		if (Strings.isEmpty(name)){
			return null;
		}
		
		for (ClusterAllocation cl : values()) {
			if (cl.getName().equals(name)){
				return cl;
			}
		}
		
		return null;
	}
}
