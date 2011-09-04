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