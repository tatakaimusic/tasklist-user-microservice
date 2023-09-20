package com.example.tasklistusermicroservice.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.NumberFormat;

public class UserDTO {
    @Email
    @NotNull(message = "Email must be not null!")
    private String email;
    @NotNull(message = "Username must be not null!")
    @Length(min = 3, max = 50, message = "Username length must be between 3 and 50 symbols!")
    private String username;
    @NotNull(message = "Password must be not null!")
    @Length(min = 3, max = 50, message = "Password length must be between 3 and 50 symbols!")
    private String password;
    @NotNull(message = "Password confirmation must be not null!")
    @Length(min = 3, max = 50, message = "Password length must be between 3 and 50 symbols!")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String passwordConfirmation;
    @NumberFormat
    @Pattern(regexp = "(\\+61|0)[0-9]{9}")
    private String phoneNumber;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
