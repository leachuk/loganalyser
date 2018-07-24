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
        System.out.println("Welcome to the Log Analyser");

         //group pages by user
        Map<String, List<LogEntryItem>> tempDataStore = getSampleData().collect(Collectors.groupingBy(LogEntryItem::getUser));
        
        //get 3-page view groups
        Map<String, List<List<String>>> sequentialPages = new TreeMap<>();
        for (Map.Entry<String, List<LogEntryItem>> user : tempDataStore.entrySet()) {
            List tempLogList = new ArrayList<String>();
            for (int i = 0; i <= user.getValue().size() - SEQUENTIAL_PAGE_COUNT; i++){
                Map<String, List<String>> tempPageList = subList(user.getValue(), i,SEQUENTIAL_PAGE_COUNT)
                                                     .stream().collect(Collectors.groupingBy(
                                                        LogEntryItem::getUser,
                                                        Collectors.mapping(LogEntryItem::getPage, Collectors.toList()))
                                                     );
                tempLogList.add(tempPageList.get(user.getKey()));
            }
            sequentialPages.put(user.getKey(), tempLogList);
        }
    
        Object test = sequentialPages.entrySet().stream().collect(Collectors.toMap(o -> o.getKey(), e -> doProcess(e.getValue())));
        List<List<String>> testFlatMap = sequentialPages.entrySet().stream().flatMap(o -> o.getValue().stream()).collect(Collectors.toList());
        Object testDistinct = sequentialPages.entrySet().stream().flatMap(o -> o.getValue().stream()).distinct().collect(Collectors.toList());
    
        Object testSize = testFlatMap.stream().collect(Collectors.groupingBy(o -> o.toString()));

        //oldschool loop to get duplicate count
        Map<Integer, Log> logMap = new HashMap<>();
        for (List itemList : testFlatMap) {
            if (!logMap.containsKey(itemList.hashCode())) {
                LogEntryList logEntryList = new LogEntryList(itemList);
                Log log = new Log(logEntryList);
                log.setOccurrences(1);
                logMap.put(itemList.hashCode(), log);
            } else {
                Log existingLog = logMap.get(itemList.hashCode());
                existingLog.setOccurrences(existingLog.getOccurrences() + 1);
            }
        }

        //output top 2 journeys by occurrence
        List<Map.Entry> ordered = logMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue((o1, o2) -> o2.getOccurrences().compareTo(o1.getOccurrences())))
                .limit(10)
                .collect(Collectors.toList());

        for (Map.Entry<Integer, Log> map : ordered) {
            System.out.println(map.getValue().getPageJourney().printList() + String.format("[%d]", map.getValue().getOccurrences()));
        }

        System.out.println("Completed Log Analyser");
    }

    private static String doProcess(List list){
        return list.toString() + ":" + list.hashCode();
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

    private static void printToConsole(List<String> transactions) {
        System.out.println("===========================================");
        for (int i = 0; i < transactions.size(); i++) {
            System.out.println("["+ i +"] " + transactions.get(i));
        }
        System.out.println("===========================================");
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
