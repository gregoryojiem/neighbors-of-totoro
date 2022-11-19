package com.example.backend.Controller;

import com.example.backend.Model.Event;
import com.example.backend.Service.EventService;
import com.google.common.hash.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.Random;
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
        if(results[0] == 1 && results[1] == 1) {
            return new ResponseEntity<>(results, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(results,HttpStatus.BAD_REQUEST);
        }
    }


    //event_has_day RELATIONSHIP
    //CREATE

    //READ

    //DELETE
    @CrossOrigin
    @DeleteMapping("/events/{eventID}/days/{dayID}")
    public ResponseEntity<Integer> deleteEventHasDay(@PathVariable UUID eventID, @PathVariable UUID dayID) {
        int rowsAffected = eventService.deleteEventHasDay(eventID, dayID);
        if(rowsAffected == 1) {
            return new ResponseEntity<>(rowsAffected, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(rowsAffected,HttpStatus.BAD_REQUEST);
        }
    }
}
