package net.mad.ads.db.db.store.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.mad.ads.db.db.AdDB;
import net.mad.ads.db.db.store.AdDBStore;
import net.mad.ads.db.definition.BannerDefinition;

public class AdDBMapStore implements AdDBStore {

	private static final Logger logger = LoggerFactory.getLogger(AdDBMapStore.class);
	
	private Map<String, BannerDefinition> banners = null;
	
	@Override
	public void open() throws IOException {
		this.banners = new HashMap<String, BannerDefinition>();
	}

	@Override
	public void close() throws IOException {
		this.banners = null;
	}

	@Override
	public void addBanner(BannerDefinition banner) throws IOException {
		this.banners.put(banner.getId(), banner);
	}

	@Override
	public void deleteBanner(String id) throws IOException {
		this.banners.remove(id);
	}

	@Override
	public BannerDefinition getBanner(String id) {
		return this.banners.get(id);
	}

	@Override
	public int size() {
		return this.banners.size();
	}

}
