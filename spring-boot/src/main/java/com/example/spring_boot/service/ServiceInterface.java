package com.example.spring_boot.service;

import com.example.spring_boot.model.User;
import java.util.List;

public interface ServiceInterface {
    List<User> getUsers();
    User getUserById(int id);
    User addUser(User user);
    boolean deleteUser(int id);
}
