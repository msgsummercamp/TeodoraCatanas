package com.example.spring_boot.servcie;

import com.example.spring_boot.model.User;
import com.example.spring_boot.repository.UserRepository;
import com.example.spring_boot.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        User user = new User(1, "Alice");
        when(userRepository.getUserById(1)).thenReturn(user);
        when(userRepository.getAllUsers()).thenReturn(List.of(user));
        when(userRepository.deleteUser(1)).thenReturn(true);
    }

    @Test
    void getUsers_shouldReturnUserList(){
        List<User> users = userService.getUsers();
        assertEquals(1, users.size());
        assertEquals("Alice", users.getFirst().getName());
    }

    @Test
    void getUserById_shouldReturnUser(){
        User result = userService.getUserById(1);
        assertNotNull(result);
        assertEquals(1, result.getId());
    }

    @Test
    void addUser_shouldReturnAddedUser() {
        User newUser = new User(2, "Bob");
        doNothing().when(userRepository).addUser(newUser);
        User addedUser = userService.addUser(newUser);
        assertNotNull(addedUser);
        assertEquals("Bob", addedUser.getName());
        verify(userRepository).addUser(newUser);
    }

    @Test
    void deleteUser_shouldReturnTrue(){
        boolean isDeleted = userService.deleteUser(1);
        assertTrue(isDeleted);
        verify(userRepository).deleteUser(1);
    }

    @Test
    void deleteUser_shouldReturnFalseWhenUserNotFound() {
        boolean isDeleted = userService.deleteUser(2);
        assertFalse(isDeleted);
    }
}
