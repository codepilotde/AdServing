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
package net.mad.ads.services.geo;

import java.sql.SQLException;

import biz.source_code.miniConnectionPoolManager.MiniConnectionPoolManager;

public interface IPLocationDB {

	public abstract void open(String db) throws ClassNotFoundException, SQLException;

	/**
	 * Falls die Datenbank von verschiedenen Services verwendet wird, kann eine
	 * Connection übergeben werden
	 * 
	 * @param con
	 */
	public abstract void open(MiniConnectionPoolManager poolmgr);

	public abstract void close() throws SQLException;

	public abstract void importCountry(String path);

	public abstract Location searchIp(String ip);

}