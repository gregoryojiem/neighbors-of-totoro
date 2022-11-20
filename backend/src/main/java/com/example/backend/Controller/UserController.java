package com.example.backend.Controller;

import com.example.backend.Model.Day;
import com.example.backend.Model.Event;
import com.example.backend.Model.TimeRange;
import com.example.backend.Model.User;
import com.example.backend.Service.UserService;
import com.google.common.hash.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    //CREATE
    @CrossOrigin
    @PostMapping(value = "/users", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UUID> createUser(@RequestBody User user) {
        //Add a new empty user to database
        User nullUser = new User();
        Object[] results = userService.createUser(nullUser);
        UUID userID = (UUID)results[1];
        //Generate a random value that's based on the id of the user created
        Random rand = new Random(userID.getMostSignificantBits() & Long.MAX_VALUE);
        int randomInt = rand.nextInt(1000000000);

        //Hash the user's password and add random value as a salt
        String hashedPass = Hashing.sha256().hashString(user.getPassword() + randomInt,
                        StandardCharsets.UTF_8).toString();

        //Update the user's password to the hashed value
        user.setPassword(hashedPass);
        userService.updateUser(userID, user);

        //Error checking
        if ((int)results[0] == 1) {
            return new ResponseEntity<>(userID, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(userID, HttpStatus.BAD_REQUEST);
        }
    }

    //READ
    @CrossOrigin
    @GetMapping("/users/{userID}")
    public ResponseEntity<User> getUser(@PathVariable UUID userID) {
        User user = userService.getUser(userID);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    //UPDATE
    @CrossOrigin
    @PutMapping(value = "/users/{userID}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> updateUser(@PathVariable UUID userID, @RequestBody User newUserInfo) {
        int rowsAffected = userService.updateUser(userID, newUserInfo);
        if(rowsAffected == 1) {
            return new ResponseEntity<>(rowsAffected, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(rowsAffected,HttpStatus.BAD_REQUEST);
        }
    }

    //DELETE
    @CrossOrigin
    @DeleteMapping("/users/{userID}")
    public ResponseEntity<Integer> deleteUser(@PathVariable UUID userID) {
        int rowsAffected = userService.deleteUser(userID);
        if(rowsAffected == 1) {
            return new ResponseEntity<>(rowsAffected, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(rowsAffected,HttpStatus.BAD_REQUEST);
        }
    }

    @CrossOrigin
    @GetMapping("/users/{userID}/events/{eventID}")
    public ResponseEntity<Event> getUserEvent(@PathVariable UUID userID, @PathVariable UUID eventID) {
        Event event = userService.getUserEvent(userID, eventID);
        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/users/{userID}/events")
    public ResponseEntity<List<Event>> getUserEvents(@PathVariable UUID userID) {
        List<Event> events = userService.getUserEvents(userID);
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping(value = "/users/{userID}/events/{eventID}")
    public ResponseEntity<Integer> createUserParticipatesEvent(@PathVariable UUID userID, @PathVariable UUID eventID) {
        int rowsAffected = userService.createUserParticipatesEvent(userID, eventID);
        if(rowsAffected == 1) {
            return new ResponseEntity<>(rowsAffected, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(rowsAffected,HttpStatus.BAD_REQUEST);
        }
    }

    @CrossOrigin
    @DeleteMapping(value = "/users/{userID}/events/{eventID}")
    public ResponseEntity<Integer> deleteUserParticipatesEvent(@PathVariable UUID userID, @PathVariable UUID eventID) {
        int rowsAffected = userService.deleteUserParticipatesEvent(userID, eventID);
        if(rowsAffected == 1) {
            return new ResponseEntity<>(rowsAffected, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(rowsAffected,HttpStatus.BAD_REQUEST);
        }
    }

    //user invites user RELATIONSHIP
    //CREATE
    @CrossOrigin
    @PostMapping(value = "/events/{eventID}/invites/{inviterID}/-/{inviteeID}")
    public ResponseEntity<Object[]> createInvitation(@PathVariable UUID inviterID, @PathVariable UUID inviteeID,
                                                     @PathVariable UUID eventID) {
        Object[] results = userService.createInvitation(inviterID, inviteeID, eventID);
        if ((int) results[0] == 1) {
            return new ResponseEntity<>(results, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(results, HttpStatus.BAD_REQUEST);
        }
    }

    //READ
    @CrossOrigin
    @GetMapping(value = "/events/{inviteeID}/invites")
    public ResponseEntity<List<Event>> getAllEventByInvitationsReceived(@PathVariable UUID inviteeID) {
        List<Event> events = userService.getAllEventByInvitationsReceived(inviteeID);
        if (events != null) {
            return new ResponseEntity<>(events, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //DELETE
    @CrossOrigin
    @DeleteMapping(value = "/events/{eventID}/invites/{inviterID}/-/{inviteeID}")
    public ResponseEntity<Integer> deleteInvitation(@PathVariable UUID inviterID, @PathVariable UUID inviteeID,
                                                    @PathVariable UUID eventID) {
        int rowsAffected = userService.deleteInvitation(inviterID, inviteeID, eventID);
        if(rowsAffected == 1) {
            return new ResponseEntity<>(rowsAffected, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(rowsAffected,HttpStatus.BAD_REQUEST);
        }
    }

    //UserAvailabilityDay RELATIONSHIP
    //CREATE
    @CrossOrigin
    @PostMapping(value = "/users/{userID}/days/{dayID}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> createUserAvailabilityDay(@RequestBody TimeRange timeRange,
                                                             @PathVariable UUID userID, @PathVariable UUID dayID) {
        int rowsAffected = userService.createUserAvailabilityDay(timeRange, userID, dayID);
        if(rowsAffected == 1) {
            return new ResponseEntity<>(rowsAffected, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(rowsAffected,HttpStatus.BAD_REQUEST);
        }
    }

    //GET
    @CrossOrigin
    @GetMapping(value = "/users/{userID}/days/{dayID}")
    public ResponseEntity<List<TimeRange>> getAllAvailabilityRangesForDay(@PathVariable UUID userID, @PathVariable UUID dayID) {
        List<TimeRange> timeRanges = userService.getAllAvailabilityRangesForDay(userID, dayID);
        if (timeRanges != null) {
            return new ResponseEntity<>(timeRanges, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //DELETE
    @CrossOrigin
    @DeleteMapping(value = "/users/{userID}/days/{dayID}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> deleteUserAvailabilityDay(@RequestBody TimeRange timeRange,
                                                             @PathVariable UUID userID, @PathVariable UUID dayID) {
        int rowsAffected = userService.deleteUserAvailabilityDay(timeRange, userID, dayID);
        if(rowsAffected == 1) {
            return new ResponseEntity<>(rowsAffected, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(rowsAffected,HttpStatus.BAD_REQUEST);
        }
    }

    @CrossOrigin
    @GetMapping("/verify/{userID}/{hashedPass}/{pass}")
    public ResponseEntity<Boolean> verifyPassword(@PathVariable UUID userID, @PathVariable String hashedPass,
                                                  @PathVariable String pass) {
        return new ResponseEntity<>(userService.verifyPassword(userID, hashedPass, pass), HttpStatus.OK);
    }
}
