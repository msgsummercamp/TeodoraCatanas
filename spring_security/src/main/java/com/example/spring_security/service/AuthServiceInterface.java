package com.example.spring_security.service;

import com.example.spring_security.dto.SignInRequest;
import com.example.spring_security.dto.SignInResponse;
import com.example.spring_security.dto.UserRegisterRequest;

public interface AuthServiceInterface {
    SignInResponse login(SignInRequest request);
    void register(UserRegisterRequest request);
}
