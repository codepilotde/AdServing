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
