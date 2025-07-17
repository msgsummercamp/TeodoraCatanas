package com.example.spring_boot.controller;

import com.example.spring_boot.model.User;
import com.example.spring_boot.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getUsers() {
        log.info("Getting all users");
        return userService.getUsers();
    }
    @GetMapping("/{id}")
    public User getUserById(@PathVariable int id) {
        log.info("Getting user with id: {}", id);
        return userService.getUserById(id);
    }
    @PostMapping
    public ResponseEntity<User> addUser(@Valid @RequestBody User user) {
        log.info("Adding new user: {}", user);
        User createdUser = userService.addUser(user);
        log.info("Created user with id: {}", createdUser.getId());
        return ResponseEntity.ok(createdUser);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUserById(@PathVariable int id) {
        log.info("Attempting to deleting user with id: {}", id);
        User deletedUser = userService.getUserById(id);
        boolean isDeleted = userService.deleteUser(id);
        if (isDeleted) {
            log.info("Deleted user with id: {}", id);
            return ResponseEntity.ok(deletedUser);
        } else {
            log.warn("User with id: {} not found", id);
            return ResponseEntity.notFound().build();
        }
    }
}




