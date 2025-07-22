package com.example.spring_data_jpa.controller;

import com.example.spring_data_jpa.model.User;
import com.example.spring_data_jpa.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<User> getUsers(@Valid @RequestParam(required = false) String email, @Valid @RequestParam(required = false) String username) {
            if (email != null) {
                log.info("Fetching user with email: {}", email);
                return List.of(userService.findUserByEmail(email)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")));
            }
            if (username != null) {
                log.info("Fetching user with username: {}", username);
                return List.of(userService.findUserByUsername(username)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")));
            }
            log.info("Fetching all users");
            return userService.findAllUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable int id) {
        log.info("Fetching user with id: {}", id);
        return userService.findUserById(id);
    }

    @PostMapping
    public User addUser(@Valid @RequestBody User user) {
        log.info("Adding new user: {}", user);
        return userService.saveUser(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable int id) {
        log.info("Attempting to delete user with id: {}", id);
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
        log.info("Updating user: {}", user);
        return userService.updateUser(user);
    }

    @GetMapping("/count")
    public long countUsers() {
        log.info("Counting all users");
        return userService.countUsers();
    }
}
