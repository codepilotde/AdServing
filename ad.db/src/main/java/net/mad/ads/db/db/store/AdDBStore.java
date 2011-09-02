package net.mad.ads.db.db.store;

import java.io.IOException;

import net.mad.ads.db.definition.BannerDefinition;

public interface AdDBStore {
	
	public void open() throws IOException;
	public void close() throws IOException;
	
	public void addBanner (BannerDefinition banner) throws IOException;
	public void deleteBanner (String id) throws IOException;
	
	public BannerDefinition getBanner (String id);
	
	public int size ();
}
