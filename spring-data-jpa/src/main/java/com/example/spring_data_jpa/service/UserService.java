package com.example.spring_data_jpa.service;

import com.example.spring_data_jpa.model.User;
import com.example.spring_data_jpa.repository.UserRepositoryInterface;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserService {
    private final UserRepositoryInterface userRepository;

    public List<User> findAllUsers() {
        log.info("Fetching all users from the database");
        return userRepository.findAll();
    }

    public User findUserById(int id) {
        log.info("Fetching user with id: {}", id);
        return userRepository.findById(id).orElse(null);
    }

    public User saveUser(User user) {
        log.info("Saving user: {}", user);
        return userRepository.save(user);
    }

    public User deleteUser(int id) {
        log.info("Attempting to delete user with id: {}", id);
        User user = findUserById(id);
        if (user != null) {
            log.info("Deleting user: {}", user);
            userRepository.delete(user);
            return user;
        }
        log.warn("User with id: {} not found", id);
        return null;
    }

    public User updateUser(User user) {
        log.info("Updating user: {}", user);
        if (userRepository.existsById(user.getId())) {
            log.info("User with id: {} exists, proceeding with update", user.getId());
            return userRepository.save(user);
        }
        log.warn("User with id: {} does not exist, cannot update", user.getId());
        return null;
    }

    public Optional<User> findUserByUsername(@RequestBody String username) {
        log.info("Fetching users with name: {}", username);
        return userRepository.findByUsername(username);
    }

    public Optional<User> findUserByEmail(@RequestBody String email) {
        log.info("Fetching users with email: {}", email);
        return userRepository.findByEmail(email);
    }
}


