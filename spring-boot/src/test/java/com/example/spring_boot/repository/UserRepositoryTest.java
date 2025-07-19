package com.example.spring_boot.repository;

import com.example.spring_boot.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest {
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository = new UserRepository();
    }

    @Test
    void getUsers_shouldReturnAllUsers() {
        List<User> users = userRepository.getAllUsers();
        assertEquals(3, users.size());
    }

    @Test
    void getUserById_shouldReturnUser() {
        User user = userRepository.getUserById(1);
        assertNotNull(user);
        assertEquals("Alice", user.getName());
    }

    @Test
    void addUser_shouldAddUser() {
        User newUser = new User(4, "David");
        userRepository.addUser(newUser);
        User retrievedUser = userRepository.getUserById(4);
        assertNotNull(retrievedUser);
        assertEquals("David", retrievedUser.getName());
    }

    @Test
    void deleteUser_shouldDeleteUser() {
        boolean result = userRepository.deleteUser(1);
        assertTrue(result);
        assertNull(userRepository.getUserById(1));
    }

    @Test
    void deleteUser_shouldReturnFalseForNonExistentUser() {
        boolean result = userRepository.deleteUser(3);
        assertTrue(result);
    }
}
