package net.mad.ads.server.utils.listener.configuration.development;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.AbstractModule;

import net.mad.ads.base.api.EmbeddedBaseContext;
import net.mad.ads.base.api.track.TrackingService;
import net.mad.ads.base.api.track.impl.local.LocalTrackService;
import net.mad.ads.base.api.track.impl.local.bdb.BDBTrackingService;
import net.mad.ads.server.utils.AdServerConstants;
import net.mad.ads.server.utils.RuntimeContext;
import de.marx.services.geo.IPLocationDB;
import de.marx.services.geo.MaxmindIpLocationDB;

public class DevelopmentModule extends AbstractModule {

	private static final Logger logger = LoggerFactory.getLogger(DevelopmentModule.class);
	
	@Override
	protected void configure() {
		
		try {
			EmbeddedBaseContext baseContext = new EmbeddedBaseContext();
			baseContext.put(EmbeddedBaseContext.EMBEDDED_DB_DIR, RuntimeContext.getProperties().getProperty(AdServerConstants.CONFIG.PROPERTIES.TRACK_DIR));
			
			TrackingService trackService = new BDBTrackingService();
			trackService.open(baseContext);
			
//			bind(TrackingService.class).toInstance(new LocalTrackService());
			
			bind(TrackingService.class).toInstance(trackService);
			bind(IPLocationDB.class).toInstance(new MaxmindIpLocationDB());
		} catch (Exception e) {
			logger.error("", e);
		}
	}

}
