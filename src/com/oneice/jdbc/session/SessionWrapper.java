package com.oneice.jdbc.session;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import org.apache.log4j.Logger;

import com.oneice.jdbc.exe.SqlExecutor;
import com.oneice.tree.support.logging.LogFactory;

public class SessionWrapper {
	private Logger logger = LogFactory.getLog(SessionWrapper.class);
	private Connection connection;
	private boolean autoCommit = true;
	private SqlExecutor sqlExecutor;
	private Class clazz;
	private Map<String,String> keyMap;
	
	
	
	public SessionWrapper() {
	}
	
	public Class getClazz() {
		return clazz;
	}

	public void setClazz(Class clazz) {
		this.clazz = clazz;
	}

	public Connection getConnection() {
		return connection;
	}
	public void setConnection(Connection connection) {
		this.connection = connection;
	}
	public boolean isAutoCommit() {
		return autoCommit;
	}
	public void setAutoCommit(boolean autoCommit) {
		this.autoCommit = autoCommit;
		setAutoComm(autoCommit);
	}
	public SqlExecutor getSqlExecutor() {
		return sqlExecutor;
	}
	public void setSqlExecutor(SqlExecutor sqlExecutor) {
		this.sqlExecutor = sqlExecutor;
		sqlExecutor.setKeyMap(keyMap);
	}
	
	public Map<String, String> getKeyMap() {
		return keyMap;
	}

	public void setKeyMap(Map<String, String> keyMap) {
		this.keyMap = keyMap;
	}

	public Object query(String sql,Map map,Object... params){
		try {
			sqlExecutor.setKeyMap(map);
			return sqlExecutor.executeQuery(connection,sql, clazz, params);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e);
		}
		return null;
	}
	public int update(String sql,Object... params){
		return sqlExecutor.executeUpdate(connection,sql, params);
	}
	
	public void setAutoComm(boolean autoComm){
		try {
			connection.setAutoCommit(autoComm);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error("设置自动提交出错",e);
		}
	}
}
