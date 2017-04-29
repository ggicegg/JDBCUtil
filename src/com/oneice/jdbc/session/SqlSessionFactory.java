package com.oneice.jdbc.session;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.oneice.jdbc.exe.SqlExecutor;
import com.oneice.tree.pool.TreeDataSourceFactory;
import com.oneice.tree.support.logging.LogFactory;

public class SqlSessionFactory {
	private static final Logger logger = LogFactory.getLog(SqlSessionFactory.class);
	private static Class clazz;
	private static DataSource dataSource;
	static{
		TreeDataSourceFactory factory = new TreeDataSourceFactory();
		dataSource = factory.createDataConnectionPool();
	}

	public static SqlSession getSqlSession(Class clazz){
		try(Connection connection = dataSource.getConnection()){
			SqlExecutor sqlExecutor = new SqlExecutor();
			SqlSession session = new SqlSession();
			session.setClazz(clazz);
			session.setConnection(connection);
			session.setAutoComm(true);
			session.setSqlExecutor(sqlExecutor);
			return session;
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return null;
		
	}
	
	public static SqlSession getSqlSession(){
		return getSqlSession(null);
	}
}
