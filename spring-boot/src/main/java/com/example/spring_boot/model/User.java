package com.example.spring_boot.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class User {
    @Min(value = 1, message = "ID must be greater than 0")
    private int id;
    @NotBlank(message = "Name cannot be blank")
    private String name;

    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
