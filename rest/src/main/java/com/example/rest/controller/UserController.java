package com.example.rest.controller;

import com.example.rest.dto.UserPatchDto;
import com.example.rest.model.User;
import com.example.rest.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<Page<User>> getUsers() {
        log.info("Fetching all users");
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id").ascending());
        Page<User> users = userService.findAllUsers(pageable);
        return users.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        log.info("Fetching user with id: {}", id);
        return Optional.ofNullable(userService.findUserById(id))
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    @PostMapping
    public ResponseEntity<User> addUser(@Valid @RequestBody User user) {
        log.info("Adding new user: {}", user);
        User savedUser = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUserById(@PathVariable int id) {
        log.info("Attempting to delete user with id: {}", id);
        User deletedUser = userService.deleteUser(id);
        if (deletedUser == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }else{
            return ResponseEntity.noContent().build();
        }
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@Valid @RequestBody User user) {
        log.info("Updating user: {}", user);
        User updatedUser = userService.updateUser(user);
        if (updatedUser == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        } else {
            return ResponseEntity.ok(updatedUser);
        }
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<Optional<User>> findUserByUsername(@PathVariable String username) {
        log.info("Fetching users with name: {}", username);
        Optional<User> user = userService.findUserByUsername(username);
        if (user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        } else {
            return ResponseEntity.ok(user);
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Optional<User>> findUserByEmail(@PathVariable String email) {
        log.info("Fetching users with email: {}", email);
        Optional<User> user = userService.findUserByEmail(email);
        if (user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        } else {
            return ResponseEntity.ok(user);
        }
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countUsers() {
        log.info("Counting all users");
        long count = userService.countUsers();
        log.info("Total users count: {}", count);
        return ResponseEntity.ok(count);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<User> patchUser(@PathVariable int id,@Valid @RequestBody UserPatchDto patchDto) {
        log.info("Partially updating user with id: {}", id);
        User updatedUser = userService.patchUser(id, patchDto);
        if (updatedUser == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        } else {
            return ResponseEntity.ok(updatedUser);
        }
    }
}
