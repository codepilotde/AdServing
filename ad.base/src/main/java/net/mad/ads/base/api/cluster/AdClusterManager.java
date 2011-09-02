package net.mad.ads.base.api.cluster;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.mad.ads.base.api.cluster.helper.ClusterAllocation;

public class AdClusterManager {
	private Map<String, AdCluster> clusters = new HashMap<String, AdCluster>();
	
	public AdClusterManager () {
	}
	
	public void addCluster (AdCluster cluster) {
		synchronized (this.clusters) {
			this.clusters.put(cluster.getName(), cluster);
		}
	}
	
	public void setClusters (Map<String, AdCluster> clusters) {
		synchronized (this.clusters) {
			this.clusters = clusters;
		}
	}
	
	public AdCluster getCluster (String name) {
		return clusters.get(name);
	}
	
	/**
	 * liefert das nächste Cluster für die Verteilung
	 * @return
	 */
	public synchronized AdCluster getNextCluster () {
		List<AdCluster> cls = new ArrayList<AdCluster>();
		
		/*
		 * Nur Cluster, die für die automatische Verteilung gedacht sind
		 */
		for (AdCluster cl : clusters.values()) {
			if (cl.getAllocation().equals(ClusterAllocation.AUTOMATIC)) {
				cls.add(cl);
			}
		}
		/**
		 * Sortieren nach den freien Plätzen der Cluster
		 */
		Collections.sort(cls, new Comparator<AdCluster>() {

			@Override
			public int compare(AdCluster c1, AdCluster c2) {

				if (c1.getFreeCapacity() > c2.getFreeCapacity()) {
					return 1;
				} else if (c1.getFreeCapacity() < c2.getFreeCapacity()) {
					return -1;
				} else {
					return 0;
				}
			}
		});
		
		AdCluster c = cls.get(cls.size()-1);
		c.setFreeCapacity(c.getFreeCapacity()-1);
		
		return c;
	}
}