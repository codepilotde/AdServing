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
package net.mad.ads.server.utils.runnable;

import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.mad.ads.server.utils.RuntimeContext;



public class AdDbUpdateTask extends TimerTask {

	private static final Logger logger = LoggerFactory.getLogger(AdDbUpdateTask.class);
	
	public static final long delay = 1000 * 60 * 5;
	public static final long period = 1000 * 60 * 5;
	
	private boolean running = false;
	
	@Override
	public void run() {
		
		if (running) {
			return;
		}
		
		try {
			running = true;
			logger.debug("update banner database");
			
			if (RuntimeContext.getImporter() != null) {
				RuntimeContext.getImporter().runImport();
			}
			
			if (RuntimeContext.getAdDB() != null) {
				RuntimeContext.getAdDB().reopen();
			}
			
		} catch (Exception e) {
			logger.error("", e);
		} finally {
			running = false;
		}
	}

}
