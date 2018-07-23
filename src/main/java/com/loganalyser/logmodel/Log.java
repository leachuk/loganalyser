package com.loganalyser.logmodel;

public class Log {
    LogEntryList pageJourney;
    Integer occurrences;

    public Log(LogEntryList pageJourney) {
        this.pageJourney = pageJourney;
    }

    public LogEntryList getPageJourney() {
        return pageJourney;
    }

    public void setPageJourney(LogEntryList pageJourney) {
        this.pageJourney = pageJourney;
    }

    public Integer getOccurrences() {
        return occurrences;
    }

    public void setOccurrences(int occurrences) {
        this.occurrences = occurrences;
    }
}
