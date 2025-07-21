package com.example.spring_security.controller;

import com.example.spring_security.dto.UserPatchDto;
import com.example.spring_security.model.User;
import com.example.spring_security.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
        User user = sampleUser();
        Pageable pageable = PageRequest.of(0, 10);
        Page<User> userPage = new PageImpl<>(List.of(user), pageable, 1);
        when(userService.findAllUsers(any(Pageable.class))).thenReturn(userPage);
        mockMvc.perform(get("/users")
                        .param("page", "0")
                        .param("size", "10")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].username", is("john_doe")));
        verify(userService).findAllUsers(any(Pageable.class));
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
                .andExpect(status().isCreated())
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
                .andExpect(status().isNoContent());
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
        when(userService.findUserByUsername("john_doe")).thenReturn(Optional.of(sampleUser()));
        mockMvc.perform(get("/users/username/john_doe"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is("john_doe")));
    }

    @Test
    void testFindUserByUsername_NotFound() throws Exception {
        when(userService.findUserByUsername("unknown")).thenReturn(Optional.empty());
        mockMvc.perform(get("/users/username/unknown")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message", containsString("User not found")));
    }

    @Test
    void testFindUserByEmail_Found() throws Exception {
        when(userService.findUserByEmail("john@example.com")).thenReturn(Optional.of(sampleUser()));
        mockMvc.perform(get("/users/email/john@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is("john@example.com")));
    }

    @Test
    void testFindUserByEmail_NotFound() throws Exception {
        when(userService.findUserByEmail("unknown@email.com")).thenReturn(Optional.empty());
        mockMvc.perform(get("/users/email/unknown@email.com")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message", containsString("User not found")));
    }

    @Test
    void testCountUsers() throws Exception {
        when(userService.countUsers()).thenReturn(5L);
        mockMvc.perform(get("/users/count"))
                .andExpect(status().isOk())
                .andExpect(content().string("5"));
    }

    @Test
    void testPatchUser_Success() throws Exception {
        UserPatchDto patchDto = new UserPatchDto();
        patchDto.setEmail("new@example.com");
        User updatedUser = sampleUser();
        updatedUser.setEmail("new@example.com");
        Mockito.when(userService.patchUser(eq(1), any(UserPatchDto.class)))
                .thenReturn(updatedUser);
        mockMvc.perform(patch("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patchDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("new@example.com"))
                .andExpect(jsonPath("$.username").value("john_doe"));
    }

    @Test
    void testPatchUser_NotFound() throws Exception {
        UserPatchDto patchDto = new UserPatchDto();
        patchDto.setEmail("new@example.com");
        Mockito.when(userService.patchUser(eq(99), any(UserPatchDto.class)))
                .thenThrow(new EntityNotFoundException("User not found"));
        mockMvc.perform(patch("/users/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patchDto)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("User not found"))
                .andExpect(jsonPath("$.status").value(404));
    }
}
