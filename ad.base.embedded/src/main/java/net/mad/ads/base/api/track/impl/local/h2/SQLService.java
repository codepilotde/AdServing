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

package net.mad.ads.base.api.track.impl.local.h2;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import biz.source_code.miniConnectionPoolManager.MiniConnectionPoolManager;

public abstract class SQLService {
	
	private static final Logger logger = LoggerFactory.getLogger(SQLService.class);
	
	protected MiniConnectionPoolManager poolMgr;
	
	protected Connection getConnection () throws SQLException {
		return poolMgr.getConnection();
	}
	
	protected void releaseConnection (Connection connection) {
		if (connection == null) {
			return;
		}
		try {
			connection.setAutoCommit(true);
			connection.close();
		} catch (SQLException e) {
			logger.error("Fehler beim schließen einer Datenbankverbindung", e);
		}
	}
	
	protected void closeStatement (Statement statement) {
		if (statement == null) {
			return;
		}
		try {
			statement.close();
		} catch (SQLException e) {
			logger.error("Fehler beim schließen einer Datenbankverbindung", e);
		}
	}
	
	protected void closeBoth (Connection connection, Statement statement) {
		releaseConnection(connection);
		closeStatement(statement);
	}
}
