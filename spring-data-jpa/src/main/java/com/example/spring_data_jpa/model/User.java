package com.example.spring_data_jpa.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class User {
    private int id;
    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
}
