package com.example.spring_boot.repository;

import com.example.spring_boot.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository implements UserInterface {
    private final List<User> users = new ArrayList<>();

    public UserRepository() {
        // Adding some sample users
        users.add(new User(1, "Alice"));
        users.add(new User(2, "Bob"));
        users.add(new User(3, "Charlie"));
    }

    @Override
    public List<User> getAllUsers() {return users;}
    @Override
    public User getUserById(int id) {
        return users.stream().filter(user -> user.getId() == id).findFirst().orElse(null);
    }
    @Override
    public boolean deleteUser(int id) {
        User user = getUserById(id);
        if (user != null) {
            users.remove(user);
            return true;
        }
        return false;
    }
}
