package net.mad.ads.services.geo;



import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
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
public class MaxmindIpLocationDB implements IPLocationDB {
	
	
	private static final String jdbc_class_hsql = "org.hsqldb.jdbc.JDBCDriver";
	private static final String jdbc_url_hsql = "jdbc:hsqldb:";
	
//	private Connection conn = null;
	
	private String db;
	
	private MiniConnectionPoolManager poolMgr = null;
	
	public MaxmindIpLocationDB() {
	}
	
	/* (non-Javadoc)
	 * @see de.marx.services.geo.IPLocationDB#open()
	 */
	@Override
	public void open(String db) throws ClassNotFoundException, SQLException {
		this.db = db;
		
		Class.forName(jdbc_class_hsql);

		JDBCPooledDataSource datasource = new JDBCPooledDataSource();
//		datasource.setUrl(jdbc_url_hsql + db);
		datasource.setUser("sa");
		datasource.setPassword("");
		datasource.setDatabase(jdbc_url_hsql + db);

		poolMgr = new MiniConnectionPoolManager(datasource, 50);
	}

	/* (non-Javadoc)
	 * @see de.marx.services.geo.IPLocationDB#open(biz.source_code.miniConnectionPoolManager.MiniConnectionPoolManager)
	 */
	@Override
	public void open(MiniConnectionPoolManager poolmgr) {
		this.poolMgr = poolmgr;
	}

	/* (non-Javadoc)
	 * @see de.marx.services.geo.IPLocationDB#close()
	 */
	@Override
	public void close() throws SQLException {
		poolMgr.dispose();
	}

	/* (non-Javadoc)
	 * @see de.marx.services.geo.IPLocationDB#importCountry(java.lang.String)
	 */
	@Override
	public void importCountry(String path) {
		Connection conn = null;
		try {
			if (!path.endsWith("/")) {
				path += "/";
			}
			conn = poolMgr.getConnection();
			conn.setAutoCommit(true);
			Statement stat = conn.createStatement();
			
			
			StringBuilder builder = new StringBuilder();
			builder.append("CREATE CACHED TABLE IP_COUNTRY (");
		    builder.append("ipFROM BIGINT NOT NULL ,");
		    builder.append("ipTO BIGINT NOT NULL ,");
		    builder.append("country VARCHAR(2),");
		    builder.append("region VARCHAR(128),");
		    builder.append("city VARCHAR(128),");
		    builder.append("postalcode VARCHAR(10),");
		    builder.append("latitude DOUBLE,");
		    builder.append("longitude DOUBLE,");
		    builder.append("PRIMARY KEY(ipFROM, ipTO)");
		    builder.append(")");
		    
		    stat.execute(builder.toString());
			stat.close();
		    
			builder = new StringBuilder();
			builder.append("INSERT INTO IP_COUNTRY (");
			builder.append("ipFROM,");
		    builder.append("ipTO,");
		    builder.append("country,");
		    builder.append("region,");
		    builder.append("city,");
		    builder.append("postalcode,");
		    builder.append("latitude,");
		    builder.append("longitude) ");
		    builder.append(" VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
			
//			PreparedStatement ps = conn.prepareStatement("INSERT INTO IP_COUNTRY (ip_from, ip_to, country_code, region_name, city_name, city_zip, latitude, longitude, timezone) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
		    PreparedStatement ps = conn.prepareStatement(builder.toString());
			
			BufferedReader br = new BufferedReader(new FileReader(path + "GeoLiteCity-Blocks.csv"));
			CSVReader reader = new CSVReader(br, ',', '\"', 2);
			
//			Scanner scanner = new Scanner(new FileReader(filename));
//			boolean firstLine = true;
			int count = 0;
			String [] values;
			Map<String, Map<String,String>> locations = getLocations(path);
			while ((values = reader.readNext()) != null) {
				String ipfrom = values[0];
				String ipto = values[1];
				String locid = values[2];
				
				Map<String,String> location = locations.get(locid);
				
				ps.setLong(1, Long.valueOf(ipfrom));
				ps.setLong(2, Long.valueOf(ipto));
				ps.setString(3, location.get("country"));
				ps.setString(4, location.get("region"));
				ps.setString(5, location.get("city"));
				ps.setString(6, location.get("postalcode"));
				ps.setDouble(7, location.get("latitude").equals("") ? 0 : Double.valueOf(location.get("latitude")));
				ps.setDouble(8, location.get("longitude").equals("") ? 0 : Double.valueOf(location.get("longitude")));

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
	
	/* (non-Javadoc)
	 * @see de.marx.services.geo.IPLocationDB#searchIp(java.lang.String)
	 */
	@Override
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
	        	String c = result.getString("country");
	        	String rn = result.getString("region");
	        	String cn = result.getString("city");
	        	
	        	String lat = result.getString("latitude");
	        	String lng = result.getString("longitude");
	        	
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
	
	private Map<String, Map<String, String>> getLocations (String path) throws IOException {
		if (!path.endsWith("/")) {
			path += "/";
		}
		String filename = path + "GeoLiteCity-Location.csv";
		Map<String, Map<String, String>> result = new HashMap<String, Map<String,String>>();
		

		BufferedReader br = new BufferedReader(new FileReader(filename));
		CSVReader reader = new CSVReader(br, ',', '\"', 2);
		String [] values;
		while ((values = reader.readNext()) != null) {
			
			Map<String, String> loc = new HashMap<String, String>();
			loc.put("locid", values[0]);
			loc.put("country", values[1]);
			loc.put("region", values[2]);
			loc.put("city", values[3]);
			loc.put("postalcode", values[4]);
			loc.put("latitude", values[5]);
			loc.put("longitude", values[6]);
			
			result.put(values[0], loc);
		}
		
		return result;
	}
	
	private String mtrim (String text) {
		if (text.startsWith("\"")) {
			text = text.substring(1);
		}
		if (text.endsWith("\"")) {
			text = text.substring(0, text.length()-1);
		}
		return text;
	}
}
