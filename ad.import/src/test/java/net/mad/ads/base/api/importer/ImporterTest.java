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
