package net.mad.ads.base.api.track.impl.local.h2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.sql.ConnectionPoolDataSource;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import biz.source_code.miniConnectionPoolManager.MiniConnectionPoolManager;

import net.mad.ads.base.api.BaseContext;
import net.mad.ads.base.api.EmbeddedBaseContext;
import net.mad.ads.base.api.exception.ServiceException;
import net.mad.ads.base.api.track.Criterion;
import net.mad.ads.base.api.track.TrackingService;
import net.mad.ads.base.api.track.events.ClickTrackEvent;
import net.mad.ads.base.api.track.events.EventType;
import net.mad.ads.base.api.track.events.ImpressionTrackEvent;
import net.mad.ads.base.api.track.events.TrackEvent;

public class H2TrackingService extends SQLService implements TrackingService {
	
	private static final Logger logger = LoggerFactory.getLogger(H2TrackingService.class);

	@Override
	public void open(BaseContext context) throws ServiceException {
		// TODO Auto-generated method stub
		DataSource ds = context.get(EmbeddedBaseContext.EMBEDDED_TRACKING_DATASOURCE, DataSource.class, null);
		if (ds == null) {
			throw new ServiceException("DataSource can not be null");
		}
		int maxcon = context.get(EmbeddedBaseContext.EMBEDDED_TRACKING_DATASOURCE_MAX_CONNECTIONS, int.class, 50);
		
		this.poolMgr = new MiniConnectionPoolManager((ConnectionPoolDataSource) ds, maxcon);
		
		initTable();
		
		initTable();
	}
	
	
	@Override
	public void close() throws ServiceException {
		try {
			this.poolMgr.dispose();
		} catch (SQLException e) {
			throw new ServiceException(e.getMessage());
		}
	}

	@Override
	public void track(TrackEvent event) throws ServiceException {
		PreparedStatement  statement = null;
		Connection connection = null;
		try {
			connection = getConnection();
			String query = "INSERT INTO trackevent (id, type, site, campaign, user, bannerid, ip, created) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
			statement = connection.prepareStatement(query);
			
			statement.setString(1, event.getId());
			statement.setString(2, event.getType().getName());
			statement.setString(3, event.getSite());
			statement.setString(4, event.getCampaign());
			statement.setString(5, event.getUser());
			statement.setString(6, event.getBannerId());
			statement.setString(7, event.getIp());
			statement.setTimestamp(8, new Timestamp(event.getTime()));
			
			statement.execute();
			
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		} finally {
			closeBoth(connection, statement);
		}
		
	}

	@Override
	public List<TrackEvent> list(Criterion criterion, Date from, Date to)
			throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int count(Criterion criterion, Date from, Date to) throws ServiceException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void delete(Criterion criterion, Date from, Date to) throws ServiceException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clear(Criterion criterion) throws ServiceException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int countClicks(Criterion criterion, Date from, Date to)
			throws ServiceException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int countClicks(String user, Criterion criterion, Date from, Date to)
			throws ServiceException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int countImpressions(Criterion criterion, Date from, Date to)
			throws ServiceException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int countImpressions(String user, Criterion criterion, Date from, Date to)
			throws ServiceException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void deleteImpressions(Criterion criterion, Date from, Date to)
			throws ServiceException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteClicks(Criterion criterion, Date from, Date to)
			throws ServiceException {
		// TODO Auto-generated method stub
		
	}
	
	private TrackEvent getEvent (ResultSet rs) throws SQLException {
		String type = rs.getString("type");
		EventType et = EventType.forName(type);
		
		TrackEvent event = null;
		if (et.equals(EventType.CLICK)) {
			event = new ClickTrackEvent();
		} else if (et.equals(EventType.IMPRESSION)) {
			event = new ImpressionTrackEvent();
		}
		event.setBannerId(rs.getString("bannerid"));
		event.setId(rs.getString("id"));
		event.setIp(rs.getString("ip"));
		event.setCampaign(rs.getString("campaign"));
		event.setSite(rs.getString("site"));
		event.setUser(rs.getString("user"));
		event.setTime(rs.getTimestamp("created").getTime());
		
		
		
		return event;
		
		
	}

	private void initTable () throws ServiceException {
		Statement statement = null;
		Connection connection = null;
		try {
			connection = getConnection();
			ResultSet resultSet = connection.getMetaData().getTables("%", "%",
					"%", new String[] { "TABLE" });
//			int columnCnt = resultSet.getMetaData().getColumnCount();
			boolean shouldCreateTable = true;
			while (resultSet.next() && shouldCreateTable) {
				if (resultSet.getString("TABLE_NAME").equalsIgnoreCase(
						"trackevent")) {
					shouldCreateTable = false;
				}
			}
			resultSet.close();
			if (shouldCreateTable) {
				logger.debug("Creating Table: trackevent");
				statement = connection.createStatement();

				StringBuilder sb = new StringBuilder();
				sb.append("CREATE TABLE trackevent (");
				sb.append(" id VARCHAR (100) NOT NULL, ");
				sb.append(" type VARCHAR (10) NOT NULL, ");
				sb.append(" site VARCHAR (20) NOT NULL, ");
				sb.append(" campaign VARCHAR (20) NOT NULL, ");
				sb.append(" user VARCHAR (20) NOT NULL, ");
				sb.append(" bannerid VARCHAR(20) NOT NULL, ");
				sb.append(" ip VARCHAR (100) NOT NULL, ");
				sb.append(" created timestamp NOT NULL ");
				sb.append(" )");
				logger.debug(sb.toString());
				statement.execute(sb.toString());
				
				statement.execute("CREATE INDEX te_id ON trackevent(id)");
				statement.execute("CREATE INDEX te_type ON trackevent(type)");
				statement.execute("CREATE INDEX te_site ON trackevent(site)");
				statement.execute("CREATE INDEX te_user ON trackevent(user)");
				statement.execute("CREATE INDEX te_campaign ON trackevent(campaign)");
				statement.execute("CREATE INDEX te_bannerid ON trackevent(bannerid)");
				statement.execute("CREATE INDEX te_created ON trackevent(created)");
			} else {
				logger.debug("table trackevent already exists");
			}
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		} finally {
			closeBoth(connection, statement);
		}
	}
}
