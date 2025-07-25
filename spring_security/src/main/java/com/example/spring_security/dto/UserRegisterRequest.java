package com.example.spring_security.dto;

import lombok.Data;

import java.util.Set;

@Data
public class UserRegisterRequest {
    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Set<String> roles;
}
