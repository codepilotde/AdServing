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
