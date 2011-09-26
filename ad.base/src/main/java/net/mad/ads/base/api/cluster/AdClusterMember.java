/**
 * Mad-Advertisement
 * Copyright (C) 2011 Thorsten Marx <thmarx@gmx.net>
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

/**
 * Ein Member entspricht einem AdServer der sich in einem bestimmten Cluster befindet
 * 
 * @author tmarx
 *
 */
public class AdClusterMember {
	
	/*
	 * Der Name zur Identification des Cluster-Members
	 */
	private String name;
	private String id;
	/*
	 * Der Name des Clusters
	 */
	private String clusterName;
	private String clusterId;
	/*
	 * Host des Members
	 */
	private String host;
	/*
	 * Benutzernamen zur Authentifizierung
	 */
	private String username;
	/*
	 * Passwort zur Authentifizierung
	 */
	private String password;
	
	
	public AdClusterMember () {
		
	}

	
	public String getName() {
		return name;
	}




	public void setName(String name) {
		this.name = name;
	}




	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}

	

	public String getClusterId() {
		return clusterId;
	}


	public void setClusterId(String clusterId) {
		this.clusterId = clusterId;
	}


	public String getClusterName() {
		return clusterName;
	}



	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}



	public String getHost() {
		return host;
	}


	public String getUsername() {
		return username;
	}


	public String getPassword() {
		return password;
	}


	public void setHost(String host) {
		this.host = host;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
