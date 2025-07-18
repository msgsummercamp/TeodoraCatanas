package com.example.spring_data_jpa.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
public class User {
    @Id
    private int id;
    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
}
