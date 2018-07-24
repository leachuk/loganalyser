package com.loganalyser;

import com.loganalyser.logmodel.Log;
import com.loganalyser.logmodel.LogEntryItem;
import com.loganalyser.logprocessor.LogProcessor;
import com.loganalyser.logprocessor.LogProcessorBuilder;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class LogAnalyser {
    private static final int SEQUENTIAL_PAGE_COUNT = 3;
    private static final int LIST_LIMIT = 10;
    
    public static void main(String[] args) {
        System.out.println("================== Welcome to the Log Analyser ==================");
    
        LogProcessor logProcessor = new LogProcessorBuilder(getSampleData())
                                        .limit(3)
                                        .pageCount(3)
                                        .isDescOrder(true)
                                        .build();
        
        List<Map.Entry<Integer, Log>> ordered = logProcessor.getOrderedList();

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


}
