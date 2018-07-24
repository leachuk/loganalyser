package com.loganalyser;

import com.loganalyser.logmodel.Log;
import com.loganalyser.logmodel.LogEntryItem;
import com.loganalyser.logmodel.LogEntryList;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LogAnalyser {
    private static final int SEQUENTIAL_PAGE_COUNT = 3;
    
    public static void main(String[] args) {
        System.out.println("================== Welcome to the Log Analyser ==================");
        
        Map<String, List<LogEntryItem>> dataStore = getSampleData().collect(Collectors.groupingBy(LogEntryItem::getUser));
        
        Map<Integer, Log> logMap = new HashMap<>();
        for (Map.Entry<String, List<LogEntryItem>> user : dataStore.entrySet()) {
            for (int i = 0; i <= user.getValue().size() - SEQUENTIAL_PAGE_COUNT; i++){
                Map<String, List<String>> tempPageList = subList(user.getValue(), i,SEQUENTIAL_PAGE_COUNT)
                                                         .stream()
                                                         .collect(Collectors.groupingBy(LogEntryItem::getUser,
                                                            Collectors.mapping(LogEntryItem::getPage, Collectors.toList())));
               
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
        
        List<Map.Entry> ordered = logMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue((o1, o2) -> o2.getOccurrences().compareTo(o1.getOccurrences())))
                .limit(10)
                .collect(Collectors.toList());

        for (Map.Entry<Integer, Log> map : ordered) {
            System.out.println(map.getValue().getPageJourney().printList() + String.format("\t[%d]", map.getValue().getOccurrences()));
        }

        System.out.println("================== Completed Log Analyser ==================");
    }

    private static Stream<LogEntryItem> getSampleData(){
        Stream<LogEntryItem> logItemList = Stream.of(
            new LogEntryItem("user1", "/"),
            new LogEntryItem("user1", "login"),
            new LogEntryItem("user1", "subscriber"),
            new LogEntryItem("user2", "/"),
            new LogEntryItem("user2", "login"),
            new LogEntryItem("user2", "subscriber"),
            new LogEntryItem("user3", "/"),
            new LogEntryItem("user3", "login"),
            new LogEntryItem("user3", "product"),
            new LogEntryItem("user1", "/"),
            new LogEntryItem("user4", "/"),
            new LogEntryItem("user4", "login"),
            new LogEntryItem("user4", "product"),
            new LogEntryItem("user5", "/"),
            new LogEntryItem("user5", "login"),
            new LogEntryItem("user5", "subscriber")
        );

        return logItemList;
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
