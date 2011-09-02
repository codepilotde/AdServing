package net.mad.ads.base.api.cluster.test;


import net.mad.ads.base.api.cluster.AdCluster;
import net.mad.ads.base.api.cluster.AdClusterManager;
import net.mad.ads.base.api.cluster.helper.ClusterAllocation;

public class ClusterManagerTest {
	public static void main (String [] args) {
		
		AdClusterManager m = new AdClusterManager();
		
		AdCluster c1 = new AdCluster("eins");
		c1.setCapacity(5);
		c1.setFreeCapacity(3);
		c1.setAllocation(ClusterAllocation.AUTOMATIC);
		m.addCluster(c1);
		
		AdCluster c2 = new AdCluster("zwei");
		c2.setCapacity(10);
		c2.setFreeCapacity(2);
		c2.setAllocation(ClusterAllocation.AUTOMATIC);
		m.addCluster(c2);
		
		AdCluster c3 = new AdCluster("drei");
		c3.setCapacity(10);
		c3.setAllocation(ClusterAllocation.MANUAL);
		m.addCluster(c3);
		
		AdCluster c = m.getNextCluster();
		System.out.println(c.getName() + " " + c.getFreeCapacity());
	}
}
