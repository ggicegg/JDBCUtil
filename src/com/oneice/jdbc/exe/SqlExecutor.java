package com.oneice.jdbc.exe;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.oneice.jdbc.handler.ListBeanResultSetHandler;
import com.oneice.jdbc.handler.ResultSetHandler;
import com.oneice.jdbc.handler.SimpleBeanResultSetHandler;
import com.oneice.jdbc.logging.LogFactory;

/**
 * 可实现自定义结果处理器的sql语句执行类
 * @author ice
 *
 */
public class SqlExecutor {
	private final Logger logger = LogFactory.getLog(SqlExecutor.class);
	private DataSource dataSource;
	private Map<String,String> keyMap;
	private ResultSetHandler handler;
	
	public SqlExecutor(){
		
	}
	
	public SqlExecutor(DataSource dataSource){
		this.dataSource = dataSource;
	}
	
	public SqlExecutor(DataSource dataSource,Map<String,String> keyMap){
		this.dataSource = dataSource;
		this.keyMap = keyMap;
	}
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public ResultSetHandler getHandler() {
		return handler;
	}

	public void setHandler(ResultSetHandler handler) {
		this.handler = handler;
	}
	
	public Map<String, String> getKeyMap() {
		return keyMap;
	}

	public void setKeyMap(Map<String, String> keyMap) {
		this.keyMap = keyMap;
	}

	/**
	 * 根据构造函数中的默认数据库源中取出连接，进行单参数的sql语句查询，并将查询出的结果集转化为pojo对象
	 * @param sql
	 * @param param
	 * @param clazz
	 * @return
	 * @throws SQLException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public Object executeQuery(String sql,Class clazz,Object... params) throws SQLException, InstantiationException, IllegalAccessException{
		try(Connection connection = dataSource.getConnection();){
			return executeQuery(connection,sql,clazz,params);
		}
	}
	
	/**
	 * 根据给定的数据库连接，进行单参数的sql语句查询，并将查询出的结果集转化为pojo对象
	 * @param connection
	 * @param sql
	 * @param param
	 * @param clazz
	 * @return
	 * @throws SQLException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public Object executeQuery(Connection connection,String sql,Class clazz,Object... params) throws InstantiationException, IllegalAccessException, SQLException{
		try (PreparedStatement ps = connection.prepareStatement(sql);){
			try{
				for(int i = 1;i <= params.length;i++){
					ps.setObject(i, params[i-1]);
				}
			}catch(Exception e){
				
			}
			ResultSet rs=null;
			int rows=0;
			try {
				rs = ps.executeQuery();
				rs.last();
				rows = rs.getRow();
				rs.beforeFirst();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				logger.error("在执行SQL语句<--"+sql+"-->时获取结果集失败",e);
			}
			
			//如果查询出的结果集行数为1使用单bean处理器
			//如果查询出的结果集行数大于1使用list处理器
			//否则代表没有数据返回null
			if(handler == null){
				if(rows == 1){
					handler = new SimpleBeanResultSetHandler(keyMap);
				}else if(rows > 1){
					handler = new ListBeanResultSetHandler(keyMap);
				}else{
					logger.info(ps.toString()+"没有查询到结果");
					return null;
				}
			}
			return handler.handle(rs, clazz);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			logger.error("获取PreparedStatement失败",e1);
		}
		return null;
	}
	
	/**
	 * 使用默认数据库连接池的连接执行增删改三种操作
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException 
	 */
	public int executeUpdate(String sql,Object... params) throws SQLException{
		try(Connection connection = dataSource.getConnection();){
			return executeUpdate(connection,sql,params);
		}
	}
	/**
	 * 使用给定连接执行增删改三种操作
	 * @param connection
	 * @param sql
	 * @param params
	 * @return
	 */
	public int executeUpdate(Connection connection,String sql,Object... params){
		int result = 0;
		
		try (PreparedStatement ps = connection.prepareStatement(sql);){
			try{
				for(int i = 1;i <= params.length;i++){
					ps.setObject(i, params[i-1]);
				}
			}catch(Exception e){
				
			}
			try {
				result = ps.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				logger.info(ps.toString()+"执行出错");
			}
			return result;
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			logger.error("获取PreparedStatement失败",e1);
		}
		return result;
	}
}
