package com.loganalyser.logmodel;

public class Log {
    LogEntryList pageJourney;
    int occurrences;

    public Log(LogEntryList pageJourney) {
        this.pageJourney = pageJourney;
    }

    public LogEntryList getPageJourney() {
        return pageJourney;
    }

    public void setPageJourney(LogEntryList pageJourney) {
        this.pageJourney = pageJourney;
    }

    public int getOccurrences() {
        return occurrences;
    }

    public void setOccurrences(int occurrences) {
        this.occurrences = occurrences;
    }
}
