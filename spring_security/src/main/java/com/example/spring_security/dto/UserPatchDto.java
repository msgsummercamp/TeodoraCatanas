package com.example.spring_security.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPatchDto {
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    private String username;
    @Pattern(
            regexp = ".+@.+\\.[a-zA-Z]+",
            message = "Email should be in a valid format (e.g. name@email.com)"
    )
    private String email;
    private String password;
    private String firstName;
    private String lastName;
}
