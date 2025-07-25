package com.example.spring_security.service;

import com.example.spring_security.dto.SignInRequest;
import com.example.spring_security.dto.SignInResponse;
import com.example.spring_security.dto.UserRegisterRequest;
import com.example.spring_security.model.Role;
import com.example.spring_security.model.User;
import com.example.spring_security.repository.RoleRepository;
import com.example.spring_security.repository.UserRepositoryInterface;
import com.example.spring_security.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService implements AuthServiceInterface {
    private final AuthenticationManager authenticationManager;
    private final UserRepositoryInterface userRepo;
    private final JwtService jwtService;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public SignInResponse login(SignInRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        User user = userRepo.findByUsername(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        String token = jwtService.generateToken(user);
        List<String> roles = user.getRoles().stream().map(Role::getName).toList();

        return new SignInResponse(token, roles);
    }

    @Override
    public void register(UserRegisterRequest request) {
        if (userRepo.findByUsername(request.getUsername()).isPresent())
            throw new RuntimeException("Username already taken");

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setRoles(fetchRolesByNames(request.getRoles()));
        userRepo.save(user);
    }

    private Set<Role> fetchRolesByNames(Set<String> roleNames) {
        return roleNames.stream()
                .map(name -> roleRepository.findByName(name)
                        .orElseThrow(() -> new RuntimeException("Role not found: " + name)))
                .collect(Collectors.toSet());
    }
}
