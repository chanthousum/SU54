package com.setec.su54_pos_api.controllers;

import java.util.HashMap;
import java.util.Map;

import com.setec.su54_pos_api.models.User;
import com.setec.su54_pos_api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1/user/")
public class UserController {
    @Autowired
    final private UserService _userService;

    public UserController(UserService userService) {
        super();
        this._userService = userService;
    }

    @GetMapping("")
    public ResponseEntity<?> getAllUsers() {
        var users = this._userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        this._userService.createUser(user);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "User created successfully");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> findUserById(@PathVariable int id) {
        var user = this._userService.findUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/findByName")
    public ResponseEntity<?> findUserByName(@RequestParam("name") String name) {
        var users = this._userService.findUserByName(name);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable int id) {
        this._userService.deleteUserById(id);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "User deleted successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateUserById(@PathVariable int id, @RequestBody User user) {
        this._userService.updateUserById(id, user);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "User updated successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
