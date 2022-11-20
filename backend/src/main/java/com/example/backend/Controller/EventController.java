package com.example.backend.Controller;

import com.example.backend.Model.TimeRange;
import com.example.backend.Model.Day;
import com.example.backend.Model.Event;

import com.example.backend.Service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class EventController {

    @Autowired
    private EventService eventService;

    //CREATE
    @CrossOrigin
    @PostMapping(value = "/events", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UUID> createEvent(@RequestBody Event event) {
        Object[] results = eventService.createEvent(event);
        UUID eventID = (UUID)results[1];
        if ((int)results[0] == 1) {
            return new ResponseEntity<>(eventID, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(eventID, HttpStatus.BAD_REQUEST);
        }
    }

    //READ
    @CrossOrigin
    @GetMapping("/events/{eventID}")
    public ResponseEntity<Event> getEvent(@PathVariable UUID eventID) {
        Event event = eventService.getEvent(eventID);
        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    //UPDATE
    @CrossOrigin
    @PutMapping(value = "/events/{eventID}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> updateEvent(@PathVariable UUID eventID, @RequestBody Event newEventInfo) {
        int rowsAffected = eventService.updateEvent(eventID, newEventInfo);
        if(rowsAffected == 1) {
            return new ResponseEntity<>(rowsAffected, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(rowsAffected,HttpStatus.BAD_REQUEST);
        }
    }

    //DELETE
    @CrossOrigin
    @DeleteMapping("/events/{eventID}")
    public ResponseEntity<int[]> deleteEvent(@PathVariable UUID eventID) {
        int[] results = eventService.deleteEvent(eventID);
        if(results[1] == 1) {
            return new ResponseEntity<>(results, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(results,HttpStatus.BAD_REQUEST);
        }
    }

    //event_has_day RELATIONSHIP
    //CREATE
    @CrossOrigin
    @PostMapping(value = "/events/{eventID}/days/{dayID}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object[]> createEventHasDay(@PathVariable UUID eventID, @PathVariable UUID dayID,
                                                      @RequestBody TimeRange range) {
        Object[] results = eventService.createEventHasDay(eventID, dayID, range);
        if ((int) results[0] == 1) {
            return new ResponseEntity<>(results, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(results, HttpStatus.BAD_REQUEST);
        }
    }

    //READ
    @CrossOrigin
    @GetMapping(value = "/events/{eventID}/days/{dayID}")
    public ResponseEntity<Day> getDayInEvent(@PathVariable UUID eventID, @PathVariable UUID dayID) {
        Day day = eventService.getDayInEvent(eventID, dayID);
        if (day != null) {
            return new ResponseEntity<>(day, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @CrossOrigin
    @GetMapping(value = "/events/{eventID}/days/{dayID}/time")
    public ResponseEntity<TimeRange> getTimeRangeByDayInEvent(@PathVariable UUID eventID, @PathVariable UUID dayID) {
        TimeRange range = eventService.getTimeRangeByDayInEvent(eventID, dayID);
        if (range != null) {
            return new ResponseEntity<>(range, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @CrossOrigin
    @GetMapping(value = "/events/{eventID}/days")
    public ResponseEntity<List<Day>> getAllDaysInEvent(@PathVariable UUID eventID) {
        List<Day> days = eventService.getAllDaysInEvent(eventID);
        if (days != null) {
            return new ResponseEntity<>(days, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //DELETE
    @CrossOrigin
    @DeleteMapping("/events/{eventID}/days/{dayID}")
    public ResponseEntity<Integer> deleteEventHasDay(@PathVariable UUID eventID, @PathVariable UUID dayID) {
        int rowsAffected = eventService.deleteEventHasDay(eventID, dayID);
        if(rowsAffected == 1) {
            return new ResponseEntity<>(rowsAffected, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(rowsAffected, HttpStatus.BAD_REQUEST);
        }
    }
}
