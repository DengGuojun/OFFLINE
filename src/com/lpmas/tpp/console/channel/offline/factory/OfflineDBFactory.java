package com.lpmas.tpp.console.channel.offline.factory;

import java.sql.SQLException;

import com.lpmas.framework.db.DBExecutor;
import com.lpmas.framework.db.DBFactory;
import com.lpmas.framework.db.DBObject;
import com.lpmas.framework.db.MysqlDBExecutor;
import com.lpmas.framework.db.MysqlDBObject;
import com.lpmas.tpp.console.channel.offline.config.OfflineDBConfig;

public class OfflineDBFactory extends DBFactory {

	public DBObject getDBObjectR() throws SQLException {
		return new MysqlDBObject(OfflineDBConfig.DB_LINK_OFFLINE_R);
	}

	public DBObject getDBObjectW() throws SQLException {
		return new MysqlDBObject(OfflineDBConfig.DB_LINK_OFFLINE_W);
	}

	@Override
	public DBExecutor getDBExecutor() {
		return new MysqlDBExecutor();
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub

	}
}
