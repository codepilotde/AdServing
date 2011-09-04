/*
 * Mad-Advertisement Copyright (C) 2011 Thorsten Marx
 * 
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */

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
