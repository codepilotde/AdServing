package net.mad.ads.services.geo;

import java.sql.SQLException;

import biz.source_code.miniConnectionPoolManager.MiniConnectionPoolManager;

public interface IPLocationDB {

	public abstract void open(String db) throws ClassNotFoundException, SQLException;

	/**
	 * Falls die Datenbank von verschiedenen Services verwendet wird, kann eine
	 * Connection �bergeben werden
	 * 
	 * @param con
	 */
	public abstract void open(MiniConnectionPoolManager poolmgr);

	public abstract void close() throws SQLException;

	public abstract void importCountry(String path);

	public abstract Location searchIp(String ip);

}