package com.example.spring_security.validator;

import com.example.spring_security.model.User;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class ValidatorTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    private User createValidUser() {
        return new User(1, "johndoe", "john@example.com", "secret123", "John", "Doe");
    }

    @Test
    void validUser_ShouldHaveNoViolations() {
        User user = createValidUser();
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertThat(violations).isEmpty();
    }

    @Test
    void blankUsername_ShouldTriggerViolation() {
        User user = createValidUser();
        user.setUsername("");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertThat(violations)
                .anyMatch(v -> v.getPropertyPath().toString().equals("username")
                        && v.getMessage().equals("Username cannot be blank"));
    }

    @Test
    void shortUsername_ShouldTriggerViolation() {
        User user = createValidUser();
        user.setUsername("ab");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertThat(violations)
                .anyMatch(v -> v.getPropertyPath().toString().equals("username")
                        && v.getMessage().contains("between 3 and 20"));
    }

    @Test
    void blankEmail_ShouldTriggerViolation() {
        User user = createValidUser();
        user.setEmail("");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertThat(violations)
                .anyMatch(v -> v.getPropertyPath().toString().equals("email")
                        && v.getMessage().equals("Email cannot be blank"));
    }

    @Test
    void invalidEmailFormat_ShouldTriggerEmailViolation() {
        User user = createValidUser();
        user.setEmail("invalidemail");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertThat(violations)
                .anyMatch(v -> v.getMessage().equals("Email should be valid"));
    }

    @Test
    void invalidEmailDomain_ShouldTriggerPatternViolation() {
        User user = createValidUser();
        user.setEmail("john@company");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertThat(violations)
                .anyMatch(v -> v.getMessage().equals("Email should be in a valid format (e.g. name@email.com)"));
    }

    @Test
    void emailWithoutAtSymbol_ShouldFailPattern() {
        User user = createValidUser();
        user.setEmail("noatsymbol.com");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertThat(violations)
                .anyMatch(v -> v.getPropertyPath().toString().equals("email")
                        && v.getMessage().contains("valid format"));
    }

    @Test
    void emailWithoutDomain_ShouldFailPattern() {
        User user = createValidUser();
        user.setEmail("user@");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertThat(violations)
                .anyMatch(v -> v.getPropertyPath().toString().equals("email")
                        && v.getMessage().contains("valid format"));
    }

    @Test
    void emailWithoutUsername_ShouldFailPattern() {
        User user = createValidUser();
        user.setEmail("@domain.com");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertThat(violations)
                .anyMatch(v -> v.getPropertyPath().toString().equals("email")
                        && v.getMessage().contains("valid format"));
    }

    @Test
    void emailWithNumericTld_ShouldFailPattern() {
        User user = createValidUser();
        user.setEmail("user@domain.123");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertThat(violations)
                .anyMatch(v -> v.getPropertyPath().toString().equals("email")
                        && v.getMessage().contains("valid format"));
    }


    @Test
    void emailWithValidTld_ShouldPassPattern() {
        User user = createValidUser();
        user.setEmail("alice@domain.tech");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertThat(violations).noneMatch(v -> v.getPropertyPath().toString().equals("email"));
    }

    @Test
    void blankPassword_ShouldTriggerViolation() {
        User user = createValidUser();
        user.setPassword(" ");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertThat(violations)
                .anyMatch(v -> v.getPropertyPath().toString().equals("password")
                        && v.getMessage().equals("Password cannot be blank"));
    }

    @Test
    void blankFirstName_ShouldTriggerViolation() {
        User user = createValidUser();
        user.setFirstName("");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertThat(violations)
                .anyMatch(v -> v.getPropertyPath().toString().equals("firstName")
                        && v.getMessage().equals("First name cannot be blank"));
    }

    @Test
    void blankLastName_ShouldTriggerViolation() {
        User user = createValidUser();
        user.setLastName(" ");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertThat(violations)
                .anyMatch(v -> v.getPropertyPath().toString().equals("lastName")
                        && v.getMessage().equals("Last name cannot be blank"));
    }
}


