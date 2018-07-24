package com.loganalyser.logprocessor;

import com.loganalyser.logmodel.Log;
import com.loganalyser.logmodel.LogEntryItem;
import com.loganalyser.logmodel.LogEntryList;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.Map.Entry.comparingByValue;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.mapping;

public class LogProcessor {
	private Stream<LogEntryItem> data;
	private int limit;
	private int pageCount;
	private boolean isDescOrder;
	
	public LogProcessor(Stream<LogEntryItem> data) {
		this.data = data;
	}
	
	public LogProcessor(Stream<LogEntryItem> data, int limit, int pageCount, boolean isDescOrder) {
		this.data = data;
		this.limit = limit;
		this.isDescOrder = isDescOrder;
		this.pageCount = pageCount;
	}
	
	public Map<Integer, Log> doProcessLogData() {
		final int SEQUENTIAL_PAGE_COUNT = this.pageCount;


		Map<String, List<LogEntryItem>> dataStore = data.collect(groupingBy(LogEntryItem::getUser));
		Map<Integer, Log> logMap = new HashMap<>();
		for (Map.Entry<String, List<LogEntryItem>> user : dataStore.entrySet()) {
			for (int i = 0; i <= user.getValue().size() - SEQUENTIAL_PAGE_COUNT; i++){
				Map<String, List<String>> tempPageList = subList(user.getValue(), i,SEQUENTIAL_PAGE_COUNT)
																								 .stream()
																								 .collect(groupingBy(LogEntryItem::getUser,
																								 mapping(LogEntryItem::getPage, toList())));

				if (!logMap.containsKey(tempPageList.get(user.getKey()).hashCode())) {
					LogEntryList logEntryList = new LogEntryList(tempPageList.get(user.getKey()));
					Log log = new Log(logEntryList);
					log.setOccurrences(1);
					logMap.put(tempPageList.get(user.getKey()).hashCode(), log);
				} else {
					Log existingLog = logMap.get(tempPageList.get(user.getKey()).hashCode());
					existingLog.setOccurrences(existingLog.getOccurrences() + 1);
				}
			}
		}
		
		return logMap;
	}
	
	public List<Map.Entry<Integer, Log>> getOrderedList() {
		return doProcessLogData().entrySet()
					.stream()
					.sorted(comparingByValue((o1, o2) -> this.isDescOrder ? o2.getOccurrences().compareTo(o1.getOccurrences())
																													 : o1.getOccurrences().compareTo(o2.getOccurrences())))
					.limit(this.limit)
					.collect(toList());
	}
	
	private static <T> List<T> subList(List<T> list, int fromIndex, int returnListSize) {
		int size = list.size();
		if (fromIndex >= size || returnListSize <= 0 || fromIndex + returnListSize > size) {
			return Collections.emptyList();
		}
		
		fromIndex = Math.max(0, fromIndex);
		returnListSize = Math.min(size, returnListSize);
		
		return list.subList(fromIndex, fromIndex + returnListSize);
	}

}
	
