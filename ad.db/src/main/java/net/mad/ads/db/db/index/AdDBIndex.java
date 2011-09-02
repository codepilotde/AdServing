package net.mad.ads.db.db.index;

import java.io.IOException;
import java.util.List;

import net.mad.ads.db.db.request.AdRequest;
import net.mad.ads.db.definition.BannerDefinition;

public interface AdDBIndex {
	public void open() throws IOException;
	public void close() throws IOException;
	public void reopen () throws IOException;
	public void addBanner (BannerDefinition banner) throws IOException;
	public void deleteBanner (String id) throws IOException;
	public List<BannerDefinition> search (AdRequest request) throws IOException;
	
	public int size ();
}
