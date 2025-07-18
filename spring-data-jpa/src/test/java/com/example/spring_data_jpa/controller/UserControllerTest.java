package com.example.spring_data_jpa.controller;

import com.example.spring_data_jpa.model.User;
import com.example.spring_data_jpa.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private ObjectMapper objectMapper;
    private User sampleUser;

    @BeforeEach
    void setup() {
        objectMapper = new ObjectMapper();
        sampleUser = new User(1, "johndoe", "john@example.com", "password123", "John", "Doe");
    }

    @Test
    void getAllUsers_shouldReturnUserList() throws Exception {
        when(userService.findAllUsers()).thenReturn(Arrays.asList(sampleUser));
        mockMvc.perform(MockMvcRequestBuilders.get("/users"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].username").value("johndoe"));
    }

    @Test
    void getUserById_shouldReturnUser() throws Exception {
        when(userService.findUserById(1)).thenReturn(sampleUser);
        mockMvc.perform(MockMvcRequestBuilders.get("/users/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("john@example.com"));
    }

    @Test
    void addUser_shouldReturnCreatedUser() throws Exception {
        when(userService.saveUser(any(User.class))).thenReturn(sampleUser);
        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleUser)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("johndoe"));
    }

    @Test
    void updateUser_shouldReturnUpdatedUser() throws Exception {
        when(userService.updateUser(any(User.class))).thenReturn(sampleUser);
        mockMvc.perform(MockMvcRequestBuilders.put("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleUser)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("John"));
    }

    @Test
    void deleteUser_shouldReturnDeletedUser() throws Exception {
        when(userService.deleteUser(1)).thenReturn(sampleUser);
        mockMvc.perform(MockMvcRequestBuilders.delete("/users/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Doe"));
    }

    @Test
    void findUserByUsername_shouldReturnUser() throws Exception {
        when(userService.findUserByUsername("johndoe")).thenReturn(Optional.of(sampleUser));
        mockMvc.perform(MockMvcRequestBuilders.get("/users/username/johndoe"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("johndoe"));
    }

    @Test
    void findUserByEmail_shouldReturnUser() throws Exception {
        when(userService.findUserByEmail("john@example.com")).thenReturn(Optional.of(sampleUser));
        mockMvc.perform(MockMvcRequestBuilders.get("/users/email/john@example.com"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("john@example.com"));
    }
}
