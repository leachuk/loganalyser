package com.loganalyser.logmodel;

import java.util.ArrayList;
import java.util.List;

public class LogEntryList {
	List logEntryList = new ArrayList<LogEntryItem>();
	
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
