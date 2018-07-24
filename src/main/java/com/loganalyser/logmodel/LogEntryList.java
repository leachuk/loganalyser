package com.loganalyser.logmodel;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LogEntryList {
	Stream<String> logEntryList;
	
	public LogEntryList(Stream<String>  logEntryList) {
		this.logEntryList = logEntryList;
	}
	
	public Stream<String>  getLogEntryList() {
		return logEntryList;
	}
	
	public void setLogEntryList(Stream<String> logEntryList) {
		this.logEntryList = logEntryList;
	}

	public String printList() {
		String result = this.getLogEntryList()
							.map(o -> String.valueOf(o))
							.collect(Collectors.joining(" -> ", "(", ")"));

		return result;
	}

}
