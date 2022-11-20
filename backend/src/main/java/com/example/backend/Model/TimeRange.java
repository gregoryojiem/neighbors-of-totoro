package com.example.backend.Model;

import java.sql.Timestamp;

public class TimeRange {
    private Timestamp startTime;
    private Timestamp endTime;

    public TimeRange() {
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }
}
