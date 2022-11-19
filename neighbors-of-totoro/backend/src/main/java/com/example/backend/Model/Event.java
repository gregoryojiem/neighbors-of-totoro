package com.example.backend.Model;

import java.util.UUID;

public class Event {

    private UUID eventID;
    private String title;

    public Event() {
    }

    public UUID getEventID() {
        return eventID;
    }

    public void setEventID(UUID eventID) {
        this.eventID = eventID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
