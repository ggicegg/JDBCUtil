package com.oneice.jdbc.logging;

import org.apache.log4j.Logger;

public class LogFactory {
	public static Logger getLog(Class clazz){
		Logger logger = Logger.getLogger(clazz);
		return logger;
	}
}
