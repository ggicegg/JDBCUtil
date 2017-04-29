package com.test;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import com.oneice.jdbc.exe.SqlExecutor;
import com.oneice.tree.pool.TreeDataSourceFactory;


public class Test4Thread {
	private static int i = 0;
	private static int MAX_THREAD_NUM = 1000;
	private static int threadCount = 0;
	public static void main(String[] args) throws SQLException, InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
		long start = System.currentTimeMillis();
		Test4Thread d = new Test4Thread();
		TreeDataSourceFactory factory = new TreeDataSourceFactory();
		DataSource ds = factory.createDataConnectionPool();
		Map<String,String> map = new HashMap<>();
		map.put("name", "userName");
		map.put("money", "value");
		SqlExecutor executor = new SqlExecutor(ds,map);
		for(i = 0; i < MAX_THREAD_NUM;i++){
			new Thread(()->
			{
				String sql = "select * from user";
				Object[] params = new Object[0];
				List<User> users = null;
				try {
					users = d.query(executor, sql, params, i);
				} catch (InstantiationException | IllegalAccessException | SecurityException
						| IllegalArgumentException | SQLException | NoSuchMethodException | InvocationTargetException | InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				for(User user:users){
					System.out.println(user);
				}
				
				if(i == MAX_THREAD_NUM){
					long end = System.currentTimeMillis();
					System.out.println("用时:"+(end-start)+"ms");
				}
			}).start();
		}
		
		
	}

	private synchronized List<User> query(SqlExecutor executor,String sql, Object[] params,int i) throws SQLException,
			InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, InterruptedException {
		List<User> users;
		if(i < MAX_THREAD_NUM-1)
		{
			System.out.println(new Date().toLocaleString()+":"+i+"个线程被阻塞");
			wait();
		}
		users = (List<User>) executor.executeQuery(sql, User.class, params);
//		System.out.println(new Date().toLocaleStr++++++++++++++++++++++++++++++++++++++++++++++++++++++ing()+":"+i+"个线程被唤醒!");
		notifyAll();
		return users;
	}
}
