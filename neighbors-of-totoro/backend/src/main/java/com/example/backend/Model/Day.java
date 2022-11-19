package com.example.backend.Model;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.UUID;

public class Day {

    private UUID dayID;
    private String timeZone;
    private Date date;
    private Timestamp startTime;
    private Timestamp endTime;

    public Day() {
    }

    public UUID getDayID() {
        return dayID;
    }

    public void setDayID(UUID dayID) {
        this.dayID = dayID;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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
