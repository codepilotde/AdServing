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

package net.mad.ads.base.api.track.impl.local.bdb.db;


import com.sleepycat.bind.serial.ClassCatalog;
import com.sleepycat.bind.serial.SerialSerialKeyCreator;
import com.sleepycat.bind.serial.StoredClassCatalog;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.je.SecondaryConfig;
import com.sleepycat.je.SecondaryDatabase;
import net.mad.ads.base.api.track.events.TrackEvent;
import net.mad.ads.base.api.track.impl.local.helper.TrackKey;
import java.io.File;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author tmarx
 */
public class TrackDB {
	private static final Logger logger = LoggerFactory.getLogger(TrackDB.class);

	private static final String CLASS_CATALOG = "java_class_catalog";
	private static final String TRACKEVENT_STORE = "trackevent_store";
	private static final String TYPE_INDEX = "eventtype_index";
	private static final String BANNERID_INDEX = "bannerid_index";
	private static final String USER_BANNER_INDEX = "user_banner_index";

	private StoredClassCatalog javaCatalog;
	private Environment env;
	private Database trackDb;
	private SecondaryDatabase typeDb;
	private SecondaryDatabase banneridDb;
	private SecondaryDatabase user_banner_Db;
	private String homeDirectory;

	public TrackDB(String dir) {
		this.homeDirectory = dir;
	}

	public void open() throws DatabaseException {
		System.out.println("Opening environment in: " + homeDirectory);
		EnvironmentConfig envConfig = new EnvironmentConfig();
		envConfig.setTransactional(true);
		envConfig.setAllowCreate(true);
		
		
		env = new Environment(new File(homeDirectory), envConfig);

		DatabaseConfig dbConfig = new DatabaseConfig();
		dbConfig.setTransactional(true);
		dbConfig.setAllowCreate(true);
		

		Database catalogDb = env.openDatabase(null, CLASS_CATALOG, dbConfig);
		javaCatalog = new StoredClassCatalog(catalogDb);

		trackDb = env.openDatabase(null, TRACKEVENT_STORE, dbConfig);

		SecondaryConfig secConfig = new SecondaryConfig();
		secConfig.setTransactional(true);
		secConfig.setAllowCreate(true);
		secConfig.setSortedDuplicates(true);
		

		secConfig.setKeyCreator(new TrackEventByTypeKeyCreator(javaCatalog,
				TrackKey.class, TrackEvent.class, String.class));
		typeDb = env
				.openSecondaryDatabase(null, TYPE_INDEX, trackDb, secConfig);
		

		secConfig.setKeyCreator(new TrackEventByBanneridKeyCreator(javaCatalog,
				TrackKey.class, TrackEvent.class, String.class));
		banneridDb = env.openSecondaryDatabase(null, BANNERID_INDEX, trackDb,
				secConfig);
		
		secConfig.setKeyCreator(new TrackEventByUserBannerKeyCreator(javaCatalog,
				TrackKey.class, TrackEvent.class, String.class));
		user_banner_Db = env.openSecondaryDatabase(null, USER_BANNER_INDEX, trackDb,
				secConfig);
	}

	public void close() throws DatabaseException {
		// Close secondary databases, then primary databases.
		typeDb.close();
		banneridDb.close();
		user_banner_Db.close();
		trackDb.close();
		// And don't forget to close the catalog and the environment.
		javaCatalog.close();
		env.close();
	}

	public final StoredClassCatalog getClassCatalog() {

		return javaCatalog;
	}

	/**
	 * Return the shipment storage container.
	 */
	public final Database getTrackEventDatabase() {

		return trackDb;
	}

	/**
	 * Return the shipment-by-part index.
	 */
	public final SecondaryDatabase getTrackEventsByTypeDatabase() {

		return typeDb;
	}

	public final SecondaryDatabase getTrackEventsByBanneridDatabase() {

		return banneridDb;
	}
	
	public final SecondaryDatabase getUserBannerDatabase () {
		return user_banner_Db;
	}

	/**
	 * The SecondaryKeyCreator for the SupplierByCity index. This is an
	 * extension of the abstract class SerialSerialKeyCreator, which implements
	 * SecondaryKeyCreator for the case where the data keys and value are all of
	 * the serial format.
	 */
	private static class TrackEventByTypeKeyCreator extends
			SerialSerialKeyCreator<TrackKey, TrackEvent, String> {

		/**
		 * Construct the city key extractor.
		 * 
		 * @param catalog
		 *            is the class catalog.
		 * @param primaryKeyClass
		 *            is the supplier key class.
		 * @param valueClass
		 *            is the supplier value class.
		 * @param indexKeyClass
		 *            is the city key class.
		 */
		private TrackEventByTypeKeyCreator(ClassCatalog catalog,
				Class primaryKeyClass, Class valueClass, Class indexKeyClass) {

			super(catalog, primaryKeyClass, valueClass, indexKeyClass);
		}

		@Override
		public String createSecondaryKey(TrackKey pk, TrackEvent d) {
			return d.getType().getName() + "_" + pk.getTime();
		}
	}

	private static class TrackEventByBanneridKeyCreator extends
			SerialSerialKeyCreator<TrackKey, TrackEvent, String> {

		private TrackEventByBanneridKeyCreator(ClassCatalog catalog,
				Class primaryKeyClass, Class valueClass, Class indexKeyClass) {

			super(catalog, primaryKeyClass, valueClass, indexKeyClass);
		}

		@Override
		public String createSecondaryKey(TrackKey pk, TrackEvent d) {
			String key = d.getBannerId() + "_" + d.getType().getName() + "_"
					+ pk.getTime();
//			System.out.println(key);
			return key;
		}
	}

	private static class TrackEventByUserBannerKeyCreator extends
			SerialSerialKeyCreator<TrackKey, TrackEvent, String> {

		private TrackEventByUserBannerKeyCreator(ClassCatalog catalog,
				Class primaryKeyClass, Class valueClass, Class indexKeyClass) {

			super(catalog, primaryKeyClass, valueClass, indexKeyClass);
		}

		@Override
		public String createSecondaryKey(TrackKey pk, TrackEvent d) {
			String key = d.getUser() + "_" + d.getBannerId() + "_" + d.getType().getName() + "_"
					+ pk.getTime();
//			System.out.println(key);
			return key;
		}
	}
}
