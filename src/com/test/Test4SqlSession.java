package com.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.oneice.jdbc.session.SqlSession;
import com.oneice.jdbc.session.SqlSessionFactory;

public class Test4SqlSession {
	@Test
	public void test4SqlSession(){
//		SqlSessionFactory sessionFactory = new SqlSessionFactory();
		SqlSession session = SqlSessionFactory.getSqlSession(User.class);
		String sql = "select * from user";
		Map<String,String> map = new HashMap<>();
		map.put("name", "userName");
		map.put("money", "value");
		List<User> users = (List<User>) session.query(sql, map, null);
		users.forEach(System.out::println);
		
	}
}
