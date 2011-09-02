package net.mad.ads.base.api.importer;

import static org.junit.Assert.*;
import junit.framework.TestCase;

import org.junit.Test;

import net.mad.ads.db.db.AdDB;

public class ImporterTest extends TestCase {

	AdDB db = new AdDB();
	@Override
	protected void setUp() throws Exception {
		db.open();
	}
	@Override
	protected void tearDown() throws Exception {
		db.close();
	}
	
	
	@Test
	public void test() {
		Importer imp = new Importer("testdata/data/importer", db);
		
		imp.runImport();
	}

}
