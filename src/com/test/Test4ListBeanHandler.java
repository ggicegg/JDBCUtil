package com.test;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.junit.Test;

import com.oneice.jdbc.exe.SqlExecutor;
import com.oneice.tree.pool.TreeDataSourceFactory;

public class Test4ListBeanHandler {
	@Test
	public void test4Convert2List() throws InstantiationException, IllegalAccessException, SQLException{
		TreeDataSourceFactory factory = new TreeDataSourceFactory();
		DataSource ds = factory.createDataConnectionPool();
		String sql = "select * from user";
		Map<String,String> map = new HashMap<>();
		map.put("name", "userName");
		map.put("money", "value");
		SqlExecutor executor = new SqlExecutor(ds,map);
//		User user = (User) executor.executeQuery(sql, "bbb", User.class);
		List<User> list = (List<User>) executor.executeQuery(sql, User.class,null);
		if(list != null){
			for(User user:list){
				System.out.println(user);
			}
		}
		
//		System.out.println(user);
	}
}
