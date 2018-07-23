package com.loganalyser.logmodel;

import java.util.List;
import java.util.stream.Collectors;

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

	public String printList() {
		String result = this.getLogEntryList().stream()
						.map(o -> String.valueOf(o))
						.collect(Collectors.joining("->", "(", ")")).toString();

		return result;
	}

}
