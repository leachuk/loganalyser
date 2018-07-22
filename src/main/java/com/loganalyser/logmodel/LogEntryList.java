package com.loganalyser.logmodel;

import java.util.List;

public class LogEntryList {
	List<String> logEntryList;
	
	public LogEntryList(List logEntryList) {
		this.logEntryList = logEntryList;
	}
	
	public List getLogEntryList() {
		return logEntryList;
	}
	
	public void setLogEntryList(List logEntryList) {
		this.logEntryList = logEntryList;
	}
}
