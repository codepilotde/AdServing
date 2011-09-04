/*
 * Mad-Advertisement 
 * Copyright (C) 2011 Thorsten Marx
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
package net.mad.ads.base.api.service.adserver;


import java.util.List;

import net.mad.ads.base.api.BaseContext;
import net.mad.ads.base.api.cluster.AdCluster;
import net.mad.ads.base.api.cluster.AdClusterMember;
import net.mad.ads.base.api.exception.ServiceException;

public interface ClusterService {

	public void open (BaseContext context) throws ServiceException;
	public void close () throws ServiceException;
	
	/**
	 * Schreibt ein neues AdCluster in die Datenbank
	 * 
	 * @param cluster
	 * @throws ServiceException
	 */
	public String addCluster (AdCluster cluster) throws ServiceException;
	/**
	 * Schreibt einen neuen AdClusterMember in die Datenbank
	 * 
	 * @param cluster
	 * @param member
	 * @throws ServiceException
	 */
	public String addMember (AdClusterMember member) throws ServiceException;
	/**
	 * Läd ein Cluster aus der Datenbank
	 * 
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	public AdCluster getCluster (String id) throws ServiceException;
	/**
	 * liefert alle AdCluster in der Datenbank
	 * @return
	 * @throws ServiceException
	 */
	public List<AdCluster> listClusters () throws ServiceException;
	
	/**
	 * Aktuallisiert ein AdCluster in der Datenbank
	 * @param cluster
	 * @throws ServiceException
	 */
	public void updateCluster (AdCluster cluster) throws ServiceException;
	
	/**
	 * liefert alle AdClusterMember für ein Cluster aus der Datenbank
	 * @param clusterID
	 * @return
	 * @throws ServiceException
	 */
	public List<AdClusterMember> listMembers (String clusterID) throws ServiceException;
	
	/**
	 * Aktualisiert ein AdClusterMemeber in der Datenbank 
	 * 
	 * @throws ServiceException
	 */
	public void updateMember (AdClusterMember member) throws ServiceException;
	/**
	 * entfernt ein AdClusterMember aus der Datenbank
	 * @param id
	 * @throws ServiceException
	 */
	public void removeMember (String clusterId, String memberId) throws ServiceException;
	
}
