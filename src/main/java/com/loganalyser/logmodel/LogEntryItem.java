package com.loganalyser.logmodel;

public class LogEntryItem {
	String user;
	String page;
	
	public LogEntryItem(String user, String page) {
		this.user = user;
		this.page = page;
	}
	
	public String getUser() {
		return user;
	}
	
	public void setUser(String user) {
		this.user = user;
	}
	
	public String getPage() {
		return page;
	}
	
	public void setPage(String page) {
		this.page = page;
	}
}
