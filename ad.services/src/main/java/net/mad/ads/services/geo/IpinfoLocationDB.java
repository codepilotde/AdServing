package net.mad.ads.services.geo;


import java.io.BufferedReader;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import net.mad.ads.services.geo.helper.ValidateIP;

import org.hsqldb.jdbc.pool.JDBCPooledDataSource;



import au.com.bytecode.opencsv.CSVReader;
import biz.source_code.miniConnectionPoolManager.MiniConnectionPoolManager;



/**
 * Reader zum einlesen
 * 
 * "ip_start";"country_code";"country_name";"region_code";"region_name";"city";
 * "zipcode";"latitude";"longitude";"metrocode"
 * 
 * @author tmarx
 * 
 */
public class IpinfoLocationDB implements IPLocationDB {
	
	
	private static final String jdbc_class_hsql = "org.hsqldb.jdbc.JDBCDriver";
	private static final String jdbc_url_hsql = "jdbc:hsqldb:";
	
//	private Connection conn = null;
	
	private String db;
	
	private MiniConnectionPoolManager poolMgr = null;
	
	public IpinfoLocationDB() {
	}
	
	public void open(String db) throws ClassNotFoundException, SQLException {
		this.db = db;
		Class.forName(jdbc_class_hsql);
		// conn = DriverManager.getConnection(jdbc_url_hsql + db, "sa",
		// "citydb");

		JDBCPooledDataSource datasource = new JDBCPooledDataSource();
//		datasource.setUrl(jdbc_url_hsql + db);
		datasource.setUser("sa");
		datasource.setPassword("");
		datasource.setDatabase(jdbc_url_hsql + db);

		poolMgr = new MiniConnectionPoolManager(datasource, 50);
	}

	/**
	 * Falls die Datenbank von verschiedenen Services verwendet wird, kann eine
	 * Connection �bergeben werden
	 * 
	 * @param con
	 */
	public void open(MiniConnectionPoolManager poolmgr) {
		this.poolMgr = poolmgr;
	}

	public void close() throws SQLException {
		poolMgr.dispose();
	}

	public void importCountry(String filename) {
		Connection conn = null;
		try {
			conn = poolMgr.getConnection();
			conn.setAutoCommit(true);
			Statement stat = conn.createStatement();
			
			
			StringBuilder builder = new StringBuilder();
			builder.append("CREATE CACHED TABLE IP_COUNTRY (");
		    builder.append("ipFROM BIGINT NOT NULL ,");
		    builder.append("ipTO BIGINT NOT NULL ,");
		    builder.append("countrySHORT VARCHAR(2),");
		    builder.append("countryLONG VARCHAR(64),");
		    builder.append("ipREGION VARCHAR(128),");
		    builder.append("ipCITY VARCHAR(128),");
		    builder.append("ipLATITUDE DOUBLE,");
		    builder.append("ipLONGITUDE DOUBLE,");
		    builder.append("ipZIPCODE VARCHAR(10),");
		    builder.append("ipTIMEZONE VARCHAR(8),");
		    builder.append("PRIMARY KEY(ipFROM, ipTO)");
		    builder.append(")");
		    
//		    stat.execute("CREATE CACHED TABLE IP_COUNTRY(ip_from BIGINT PRIMARY KEY, ip_to BIGINT, country_code VARCHAR(4), region_name VARCHAR(100), city_name VARCHAR(50), city_zip VARCHAR(20), latitude VARCHAR(30), longitude VARCHAR(30), timezone VARCHAR(8) )");
		    stat.execute(builder.toString());
			stat.close();
		    
			builder = new StringBuilder();
			builder.append("INSERT INTO IP_COUNTRY (");
			builder.append("ipFROM,");
		    builder.append("ipTO,");
		    builder.append("countrySHORT,");
		    builder.append("countryLONG,");
		    builder.append("ipREGION,");
		    builder.append("ipCITY,");
		    builder.append("ipLATITUDE,");
		    builder.append("ipLONGITUDE,");
		    builder.append("ipZIPCODE,");
		    builder.append("ipTIMEZONE) ");
		    builder.append(" VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			
//			PreparedStatement ps = conn.prepareStatement("INSERT INTO IP_COUNTRY (ip_from, ip_to, country_code, region_name, city_name, city_zip, latitude, longitude, timezone) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
		    PreparedStatement ps = conn.prepareStatement(builder.toString());
			
			BufferedReader br = new BufferedReader(new FileReader(filename));
			CSVReader reader = new CSVReader(br, ',', '\"');
			
//			Scanner scanner = new Scanner(new FileReader(filename));
//			boolean firstLine = true;
			int count = 0;
			String [] values;
			while ((values = reader.readNext()) != null) {
				ps.setLong(1, Long.valueOf(values[0]));
				ps.setLong(2, Long.valueOf(values[1]));
				ps.setString(3, values[2]);
				ps.setString(4, values[3]);
				ps.setString(5, values[4]);
				ps.setString(6, values[5]);
				ps.setDouble(7, values[6].equals("-") ? 0 : Double.valueOf(values[6]));
				ps.setDouble(8, values[7].equals("-") ? 0 : Double.valueOf(values[7]));
				ps.setString(9, values[8]);
				ps.setString(10, values[9]);

				ps.execute();
				count++;
			}
			System.out.println(count + " Eintr�ge importiert");
			
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public Location searchIp(String ip) {
		Connection conn = null;
		Statement stat = null;
		try {
			conn = poolMgr.getConnection();
			stat = conn.createStatement();
	        
	        long inetAton = ValidateIP.ip2long(ip);
	        
	        String query = "SELECT * FROM IP_COUNTRY WHERE ipFROM <= " + inetAton + " ORDER BY ipFROM DESC LIMIT 1";
	        
//	        String query = "SELECT * FROM IP_COUNTRY WHERE " + inetAton + " BETWEEN ipFROM AND ipTO";
	        
	        ResultSet result = stat.executeQuery(query);
	        
	        while (result.next()) {
	        	String c = result.getString("countrySHORT");
	        	String rn = result.getString("ipREGION");
	        	String cn = result.getString("ipCITY");
	        	
	        	String lat = result.getString("ipLATITUDE");
	        	String lng = result.getString("ipLONGITUDE");
	        	
	        	Location loc = new Location(c, rn, cn, lat, lng);
	        	return loc;
	        }
	        
	        return Location.UNKNOWN;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
				stat.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}
	
	public String mtrim (String text) {
		if (text.startsWith("\"")) {
			text = text.substring(1);
		}
		if (text.endsWith("\"")) {
			text = text.substring(0, text.length()-1);
		}
		return text;
	}
}
