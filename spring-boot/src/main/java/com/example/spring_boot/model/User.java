package com.example.spring_boot.model;

public class User {
    private int id;
    private String name;

    //Constructor
    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }
    //Getter and Setter methods
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
