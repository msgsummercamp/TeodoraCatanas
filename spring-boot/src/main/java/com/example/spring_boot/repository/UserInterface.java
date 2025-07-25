package com.example.spring_boot.repository;

import com.example.spring_boot.model.User;
import java.util.List;

public interface UserInterface {
    List<User> getAllUsers();
    User getUserById(int id);
    void addUser(User user);
    boolean deleteUser(int id);
}
