package com.example.spring_boot.service;

import com.example.spring_boot.model.User;
import com.example.spring_boot.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements ServiceInterface{
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {this.userRepository = userRepository;}

    @Override
    public List<User> getUsers() {
        return userRepository.getAllUsers();
    }
    @Override
    public User getUserById(int id) {
        return userRepository.getUserById(id);
    }
    @Override
    public User addUser(User user) {
        userRepository.addUser(user);
        return user;
    }
    @Override
    public boolean deleteUser(int id) {
        return userRepository.deleteUser(id);
    }
}
