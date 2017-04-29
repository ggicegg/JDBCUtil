package com.oneice.jdbc.handler;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;

import org.apache.log4j.Logger;

import com.oneice.jdbc.logging.LogFactory;

public class SimpleBeanResultSetHandler implements ResultSetHandler{
	private final Logger logger = LogFactory.getLog(SimpleBeanResultSetHandler.class);
	private Map<String,String> keyMap;
	public SimpleBeanResultSetHandler(){
		
	}
	/**
	 * 构造指定数据库的label和pojo 字段名字的映射
	 * @param map
	 */
	public SimpleBeanResultSetHandler(Map<String,String> keyMap){
		this.keyMap = keyMap;
	}
	@Override
	public Object handle(ResultSet rs, Class clazz) throws SQLException, InstantiationException, IllegalAccessException {
		// TODO Auto-generated method stub
		Object object = clazz.newInstance();
		Field[] fields = clazz.getDeclaredFields();
		Object value=null;
		String key=null;
		String trueKey=null;
		ResultSetMetaData meta = rs.getMetaData();
		int columnCount = meta.getColumnCount();
		if(rs.next()){
			for(int i = 1; i <= columnCount;i++){
				value = rs.getObject(i);
				key = meta.getColumnLabel(i);
				try{
					trueKey = keyMap.get(key);
					if(trueKey == null){
						trueKey = key;
						logger.info(clazz.getName()+"的<"+key+">字段没有设置映射");
					}
				}catch(Exception e){
					trueKey = key;
					logger.info("没有设置map映射或者<"+key+">字段没有映射");
				}
				for(Field field:fields){
					String fieldName = field.getName();
					if(trueKey.equals(fieldName)){
						field.setAccessible(true);
						field.set(object, value);
					}
				}
				
			}
		}
		return object;
	}
	
}
