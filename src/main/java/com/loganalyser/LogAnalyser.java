package com.loganalyser;

import com.loganalyser.logmodel.LogEntryItem;
import com.loganalyser.logmodel.LogEntryList;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LogAnalyser {
    
    public static void main(String[] args) {
        System.out.println("Welcome to the Log Analyser");
    
        List<LogEntryItem> sampleLogData = populateSampleData().getLogEntryList();
    
        Map<String, List<LogEntryItem>> tempDataStore = sampleLogData.stream().collect(Collectors.groupingBy(LogEntryItem::getUser, TreeMap::new, Collectors.toList()));
        //tempDataStore = sampleLogData.getLogEntryList().stream().collect(Collectors.groupingBy(LogEntryItem::getUser,Collectors.groupingBy(LogEntryItem::getPage)));
        
        //work directly with stream, doesn't need to be an ArrayList.
        //https://stackoverflow.com/questions/1005073/initialization-of-an-arraylist-in-one-line
        Stream<Student> studs = Stream.of(
            new Student("1726", "John", "New York"),
            new Student("4321", "Max", "California"),
            new Student("2234", "Max", "Los Angeles"),
            new Student("7765", "Sam", "California")
        );
        Map<String, Map<String, List<Student>>> map = studs.collect(Collectors.groupingBy(Student::getStud_name,Collectors.groupingBy(Student::getStud_location)));
        System.out.println(map);
    }
    
  
    private static LogEntryList populateSampleData(){
        List logItemList = Arrays.asList(
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
        
        return new LogEntryList(logItemList);
    }

    private static void printToConsole(List<String> transactions) {
        System.out.println("===========================================");
        for (int i = 0; i < transactions.size(); i++) {
            System.out.println("["+ i +"] " + transactions.get(i));
        }
        System.out.println("===========================================");
    }
    
}

class Student {
    
    String stud_id;
    String stud_name;
    String stud_location;
    
    public String getStud_id() {
        return stud_id;
    }
    
    public String getStud_name() {
        return stud_name;
    }
    
    public String getStud_location() {
        return stud_location;
    }
    
    
    
    Student(String sid, String sname, String slocation) {
        
        this.stud_id = sid;
        this.stud_name = sname;
        this.stud_location = slocation;
        
    }
}
