package com.loganalyser.logmodel;

import java.util.ArrayList;
import java.util.List;

public class LogEntryList {
	List logEntryList = new ArrayList<>();
	private int occurrences;
	
	public LogEntryList(List logEntryList) {
		this.logEntryList = logEntryList;
	}
	
	public List getLogEntryList() {
		return logEntryList;
	}
	
	public void setLogEntryList(List logEntryList) {
		this.logEntryList = logEntryList;
	}
	
	public int getOccurrences() {
		return occurrences;
	}
	
	public void setOccurrences(int occurrences) {
		this.occurrences = occurrences;
	}
}
