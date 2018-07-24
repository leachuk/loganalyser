package com.loganalyser.logprocessor;

import com.loganalyser.logmodel.LogEntryItem;

import java.util.stream.Stream;

public class LogProcessorBuilder {
	private Stream<LogEntryItem> data;
	private int limit;
	private int pageCount;
	private boolean isDescOrder;
	
	public LogProcessorBuilder(Stream<LogEntryItem> data) {
		this.data = data;
		this.limit = 3;
		this.pageCount = 3;
		this.isDescOrder = true;
	}
	
	public LogProcessorBuilder limit(int limit) {
		this.limit = limit;
		return this;
	}
	
	public LogProcessorBuilder pageCount(int pageCount) {
		this.pageCount = pageCount;
		return this;
	}
	
	public LogProcessorBuilder isDescOrder(boolean isDescOrder) {
		this.isDescOrder = isDescOrder;
		return this;
	}
	
	public LogProcessor build(){
		return new LogProcessor(data, limit, pageCount, isDescOrder);
	}
	

}