package com.test;

import java.math.BigDecimal;

public class User {
	private String id;
	private String userName;
	private BigDecimal value;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public BigDecimal getValue() {
		return value;
	}
	public void setValue(BigDecimal value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + ", value=" + value + "]";
	}
	
	
}
