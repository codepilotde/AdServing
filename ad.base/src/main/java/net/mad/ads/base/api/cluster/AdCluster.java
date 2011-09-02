package net.mad.ads.base.api.cluster;


import java.util.ArrayList;
import java.util.List;

import net.mad.ads.base.api.cluster.helper.ClusterAllocation;

/**
 * Ein AdCluster
 * 
 * @author tmarx
 *
 */
public class AdCluster {
	/*
	 * Die Datenbank ID des Clusters 
	 */
	private String id;
	/*
	 * Der Name des Clusters
	 * Muss eindeutig sein, da er zur Identifikation dient
	 */
	private String name;
	/*
	 * Die Mitglieder dieses Clusters
	 */
	private List<AdClusterMember> members = new ArrayList<AdClusterMember>();
	
	/*
	 * Die Kapazität dieses Clusters, also wieviele User können von diesem Cluster
	 * verwaltet werden
	 */
	private int capacity = 0;
	/*
	 * Die freie Kapazität, also die Zahl von User die noch aufgenommen werden können
	 */
	private int freeCapacity = capacity;
	/*
	 * Verteilung von User auf das Cluster
	 */
	private ClusterAllocation allocation = ClusterAllocation.AUTOMATIC;
	
	public AdCluster (String name) {
		this.name = name;
	}
	
	public List<AdClusterMember> getMembers () {
		return this.members;
	}
	
	public void addMember (AdClusterMember member) {
		this.members.add(member);
	}

	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public ClusterAllocation getAllocation() {
		return allocation;
	}

	public void setAllocation(ClusterAllocation allocation) {
		this.allocation = allocation;
	}

	public int getFreeCapacity() {
		return freeCapacity;
	}

	public void setFreeCapacity(int freeCapacity) {
		this.freeCapacity = freeCapacity;
	}
	
	
	
}
