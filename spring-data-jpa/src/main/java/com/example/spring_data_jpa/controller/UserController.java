package com.example.spring_data_jpa.controller;

import com.example.spring_data_jpa.model.User;
import com.example.spring_data_jpa.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
    public List<User> getUsers() {
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
    public User deleteUserById(@PathVariable int id) {
        log.info("Attempting to delete user with id: {}", id);
        return userService.deleteUser(id);
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
        log.info("Updating user: {}", user);
        return userService.updateUser(user);
    }

    @GetMapping("/username")
    public User findUserByUsername(@RequestParam String username) {
        return userService.findUserByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    @GetMapping("/email")
    public User findUserByEmail(@RequestParam String email) {
        log.info("Fetching users with email: {}", email);
        return userService.findUserByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    @GetMapping("/count")
    public long countUsers() {
        log.info("Counting all users");
        return userService.countUsers();
    }
}
