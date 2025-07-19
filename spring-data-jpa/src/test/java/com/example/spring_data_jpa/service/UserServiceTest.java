package com.example.spring_data_jpa.service;

import com.example.spring_data_jpa.model.User;
import com.example.spring_data_jpa.repository.UserRepositoryInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepositoryInterface userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private User sampleUser() {
        return new User(1, "john_doe", "john@example.com", "secret123", "John", "Doe");
    }

    @Test
    void testFindAllUsers() {
        List<User> userList = List.of(sampleUser());
        when(userRepository.findAll()).thenReturn(userList);
        List<User> result = userService.findAllUsers();
        assertEquals(1, result.size());
        verify(userRepository).findAll();
    }

    @Test
    void testFindUserById_Found() {
        User user = sampleUser();
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        User result = userService.findUserById(1);
        assertNotNull(result);
        assertEquals("john_doe", result.getUsername());
        verify(userRepository).findById(1);
    }

    @Test
    void testFindUserById_NotFound() {
        when(userRepository.findById(2)).thenReturn(Optional.empty());
        User result = userService.findUserById(2);
        assertNull(result);
        verify(userRepository).findById(2);
    }

    @Test
    void testSaveUser() {
        User user = sampleUser();
        when(userRepository.save(user)).thenReturn(user);
        User result = userService.saveUser(user);
        assertEquals(user, result);
        verify(userRepository).save(user);
    }

    @Test
    void testDeleteUser_Found() {
        User user = sampleUser();
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        doNothing().when(userRepository).delete(user);
        User result = userService.deleteUser(1);
        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(userRepository).delete(user);
    }

    @Test
    void testDeleteUser_NotFound() {
        when(userRepository.findById(99)).thenReturn(Optional.empty());
        User result = userService.deleteUser(99);
        assertNull(result);
        verify(userRepository, never()).delete(any());
    }

    @Test
    void testUpdateUser_UserExists() {
        User user = sampleUser();
        when(userRepository.existsById(user.getId())).thenReturn(true);
        when(userRepository.save(user)).thenReturn(user);
        User result = userService.updateUser(user);
        assertNotNull(result);
        verify(userRepository).save(user);
    }

    @Test
    void testUpdateUser_UserDoesNotExist() {
        User user = sampleUser();
        when(userRepository.existsById(user.getId())).thenReturn(false);
        User result = userService.updateUser(user);
        assertNull(result);
        verify(userRepository, never()).save(any());
    }

    @Test
    void testFindUserByUsername() {
        User user = sampleUser();
        when(userRepository.findByUsername("john_doe")).thenReturn(Optional.of(user));
        Optional<User> result = userService.findUserByUsername("john_doe");
        assertTrue(result.isPresent());
        assertEquals("john_doe", result.get().getUsername());
        verify(userRepository).findByUsername("john_doe");
    }

    @Test
    void testFindUserByEmail() {
        User user = sampleUser();
        when(userRepository.findByEmail("john@example.com")).thenReturn(Optional.of(user));
        Optional<User> result = userService.findUserByEmail("john@example.com");
        assertTrue(result.isPresent());
        assertEquals("john@example.com", result.get().getEmail());
        verify(userRepository).findByEmail("john@example.com");
    }

    @Test
    void testFindUserByUsername_NotFound() {
        when(userRepository.findByUsername("unknown_user")).thenReturn(Optional.empty());
        Optional<User> result = userService.findUserByUsername("unknown_user");
        assertTrue(result.isEmpty(), "Expected no user to be found");
        verify(userRepository).findByUsername("unknown_user");
    }

    @Test
    void testFindUserByEmail_NotFound() {
        when(userRepository.findByEmail("nonexistent@example.com")).thenReturn(Optional.empty());
        Optional<User> result = userService.findUserByEmail("nonexistent@example.com");
        assertFalse(result.isPresent(), "Expected no user to be found");
        verify(userRepository).findByEmail("nonexistent@example.com");
    }

    @Test
    void testCountUsers() {
        when(userRepository.count()).thenReturn(42L);
        long result = userService.countUsers();
        assertEquals(42L, result);
        verify(userRepository, times(1)).count();
    }
}
