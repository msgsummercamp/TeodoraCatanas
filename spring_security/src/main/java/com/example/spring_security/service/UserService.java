package com.example.spring_security.service;

import com.example.spring_security.dto.UserPatchDto;
import com.example.spring_security.model.User;
import com.example.spring_security.model.Role;
import com.example.spring_security.repository.RoleRepository;
import com.example.spring_security.repository.UserRepositoryInterface;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserService {
    private final UserRepositoryInterface userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    private Set<Role> fetchRolesByNames(Set<String> roleNames) {
        return roleNames.stream()
                .map(name -> roleRepository.findByName(name)
                        .orElseThrow(() -> new RuntimeException("Role not found: " + name)))
                .collect(Collectors.toSet());
    }

    public Page<User> findAllUsers(Pageable pageable) {
        log.info("Fetching all users from the database");
        return userRepository.findAll(pageable);
    }

    public User findUserById(int id) {
        log.info("Fetching user with id: {}", id);
        return userRepository.findById(id).orElse(null);
    }

    public User saveUser(User user) {
        log.info("Saving user: {}", user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getRoles() != null && !user.getRoles().isEmpty()) {
            Set<String> roleNames = user.getRoles().stream().map(Role::getName).collect(Collectors.toSet());
            user.setRoles(fetchRolesByNames(roleNames));
        }
        return userRepository.save(user);
    }

    public User deleteUser(int id) {
        log.info("Attempting to delete user with id: {}", id);
        User user = findUserById(id);
        if (user != null) {
            log.info("Deleting user: {}", user);
            userRepository.delete(user);
            return user;
        }
        log.warn("User with id: {} not found", id);
        return null;
    }

    public User updateUser(User user) {
        log.info("Updating user: {}", user);
        if (!userRepository.existsById(user.getId())) {
            log.warn("User with id: {} does not exist, cannot update", user.getId());
            return null;
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getRoles() != null && !user.getRoles().isEmpty()) {
            Set<String> roleNames = user.getRoles().stream().map(Role::getName).collect(Collectors.toSet());
            user.setRoles(fetchRolesByNames(roleNames));
        }
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

    public User patchUser(int id, UserPatchDto patchDto) {
        log.info("Patching user with id: {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        if (patchDto.getEmail() != null) user.setEmail(patchDto.getEmail());
        if (patchDto.getPassword() != null)
            user.setPassword(passwordEncoder.encode(patchDto.getPassword()));
        if (patchDto.getUsername() != null) user.setUsername(patchDto.getUsername());
        if (patchDto.getFirstName() != null) user.setFirstName(patchDto.getFirstName());
        if (patchDto.getLastName() != null) user.setLastName(patchDto.getLastName());
        if (patchDto.getRoles() != null && !patchDto.getRoles().isEmpty())
            user.setRoles(fetchRolesByNames(patchDto.getRoles()));
        return userRepository.save(user);
    }

    @PostConstruct
    public void encodeExistingPasswords() {
        List<User> users = userRepository.findAll();
        for (User user : users) {
            String password = user.getPassword();
            // Skip if already encoded
            if (!password.startsWith("$2a$") && !password.startsWith("$2b$")) {
                String encoded = passwordEncoder.encode(password);
                user.setPassword(encoded);
                log.info("Encoded password for user: {}", user.getUsername());
            }
        }
        userRepository.saveAll(users);
    }


}


