package com.oneice.jdbc.handler;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ResultSetHandler {
	public Object handle(ResultSet rs,Class clazz) throws SQLException, InstantiationException, IllegalAccessException;
}
