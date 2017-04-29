package com.test;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.UUID;

import javax.sql.DataSource;

import org.junit.Test;

import com.oneice.jdbc.exe.SqlExecutor;
import com.oneice.tree.pool.TreeDataSourceFactory;

public class Test4Update {
	@Test
	public void test4Delete() throws SQLException{
		TreeDataSourceFactory factory = new TreeDataSourceFactory();
		DataSource ds = factory.createDataConnectionPool();
//		String sql = "delete from user where name=?";
		String sql = "insert into user values(?,?,?)";
		String uuid = UUID.randomUUID().toString();
		BigDecimal num = new BigDecimal(12.5);
		Object[] params = {uuid,"aaa",num};
		SqlExecutor executor = new SqlExecutor(ds);
		int i = executor.executeUpdate(ds.getConnection(), sql, params);
		System.out.println(i);
	}
}
