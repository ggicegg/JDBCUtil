package com.test;

import java.sql.SQLException;
import java.util.*;

import javax.sql.DataSource;

import org.junit.Test;

import com.oneice.jdbc.exe.SqlExecutor;
import com.oneice.tree.pool.TreeDataSourceFactory;

public class Test4SimpleHandler {
	
	@Test
	public void test4Convert2Bean() throws InstantiationException, IllegalAccessException, SQLException{
		TreeDataSourceFactory factory = new TreeDataSourceFactory();
		DataSource ds = factory.createDataConnectionPool();
		Map<String,String> map = new HashMap<>();
		map.put("name", "userName");
		map.put("money", "value");
		String sql = "select * from user where name=?";
		SqlExecutor executor = new SqlExecutor(ds,map);
		User user = (User) executor.executeQuery(sql, User.class,"bbb");
		System.out.println(user);
	}
}
