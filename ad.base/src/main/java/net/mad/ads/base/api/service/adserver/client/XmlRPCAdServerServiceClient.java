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
package net.mad.ads.base.api.service.adserver.client;


import java.net.URL;

import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.apache.xmlrpc.client.util.ClientFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.mad.ads.base.api.service.adserver.AdServerService;

public abstract class XmlRPCAdServerServiceClient {
	
	private static final Logger logger = LoggerFactory.getLogger(XmlRPCAdServerServiceClient.class); 
	
	
	public static final AdServerService get (String host, String username, String password) {
		try {
			XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
	        config.setServerURL(new URL(host));

	        if (username != null) {
	        	config.setBasicUserName(username);
	        	config.setBasicPassword(password);
	        }
	        config.setEnabledForExtensions(true);  
	        config.setConnectionTimeout(60 * 1000);
	        config.setReplyTimeout(60 * 1000);
	        
	        XmlRpcClient client = new XmlRpcClient();
	        client.setConfig(config);
	        ClientFactory factory = new ClientFactory(client);
	        
	        return (AdServerService) factory.newInstance(AdServerService.class);
		} catch (Exception e) {
			logger.error("error createing BannerService-Client for: " + host, e);
		}
		
		return null;
	}
}
