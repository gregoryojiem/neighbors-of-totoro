package com.example.backend.Controller;

import com.example.backend.Model.User;
import com.example.backend.Service.UserService;
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
}
