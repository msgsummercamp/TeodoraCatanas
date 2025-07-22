package com.example.spring_data_jpa.service;

import com.example.spring_data_jpa.model.User;
import com.example.spring_data_jpa.repository.UserRepositoryInterface;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
    }

    public User saveUser(User user) {
        log.info("Saving user: {}", user);
        return userRepository.save(user);
    }

    public void deleteUser(int id) {
        log.info("Attempting to delete user with id: {}", id);
        findUserById(id);
        log.info("Deleting user with id: {}", id);
        userRepository.deleteById(id);
    }

    public User updateUser(User user) {
        log.info("Updating user: {}", user);
        findUserById(user.getId());
        log.info("User with id: {} exists, proceeding with update", user.getId());
        return userRepository.save(user);
    }

    public Optional<User> findUserByUsername(String username) {
        log.info("Fetching users with name: {}", username);
        return userRepository.findByUsername(username);
    }

    public Optional<User> findUserByEmail(String email) {
        log.info("Fetching users with email: {}", email);
        return userRepository.findByEmail(email);
    }

    public long countUsers() {
        log.info("Counting all users in the database");
        return userRepository.count();
    }
}


