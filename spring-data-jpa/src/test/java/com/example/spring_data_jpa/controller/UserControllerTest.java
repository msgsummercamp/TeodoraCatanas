package com.example.spring_data_jpa.controller;

import com.example.spring_data_jpa.model.User;
import com.example.spring_data_jpa.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private User sampleUser() {
        return new User(1, "john_doe", "john@example.com", "secret123", "John", "Doe");
    }

    @Test
    void testGetUsers() throws Exception {
        when(userService.findAllUsers()).thenReturn(List.of(sampleUser()));
        mockMvc.perform(get("/users").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username", is("john_doe")));
    }

    @Test
    void testGetUserById() throws Exception {
        when(userService.findUserById(1)).thenReturn(sampleUser());
        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is("john@example.com")));
    }

    @Test
    void testAddUser_ValidInput() throws Exception {
        User user = sampleUser();
        when(userService.saveUser(any(User.class))).thenReturn(user);
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user.getId()))
                .andExpect(jsonPath("$.username").value(user.getUsername()))
                .andExpect(jsonPath("$.email").value(user.getEmail()))
                .andExpect(jsonPath("$.firstName").value(user.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(user.getLastName()));
    }

    @Test
    void testAddUser_InvalidEmail() throws Exception {
        User user = sampleUser();
        user.setEmail("invalid-email");
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testDeleteUserById() throws Exception {
        when(userService.deleteUser(1)).thenReturn(sampleUser());
        mockMvc.perform(delete("/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    void testUpdateUser() throws Exception {
        User updated = sampleUser();
        updated.setFirstName("Updated");
        when(userService.updateUser(any(User.class))).thenReturn(updated);
        mockMvc.perform(put("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(updated.getId()))
                .andExpect(jsonPath("$.username").value(updated.getUsername()))
                .andExpect(jsonPath("$.email").value(updated.getEmail()))
                .andExpect(jsonPath("$.firstName").value(updated.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(updated.getLastName()));
    }

    @Test
    void testFindUserByUsername_Found() throws Exception {
        User user = sampleUser();
        when(userService.findUserByUsername(user.getUsername())).thenReturn(Optional.of(user));
        mockMvc.perform(get("/users/username")
                        .param("username", user.getUsername()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is(user.getUsername())));
    }

    @Test
    void testFindUserByUsername_NotFound() throws Exception {
        when(userService.findUserByUsername("unknown")).thenReturn(Optional.empty());
        mockMvc.perform(get("/users/username")
                        .param("username", "unknown"))
                .andExpect(status().isNotFound())
                .andExpect(status().reason(containsString("User not found")));
    }

    @Test
    void testFindUserByEmail_Found() throws Exception {
        when(userService.findUserByEmail("john@example.com")).thenReturn(Optional.of(sampleUser()));
        mockMvc.perform(get("/users/email")
                        .param("email", "john@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is("john@example.com")));
    }

    @Test
    void testCountUsers() throws Exception {
        when(userService.countUsers()).thenReturn(5L);
        mockMvc.perform(get("/users/count"))
                .andExpect(status().isOk())
                .andExpect(content().string("5"));
    }
}
