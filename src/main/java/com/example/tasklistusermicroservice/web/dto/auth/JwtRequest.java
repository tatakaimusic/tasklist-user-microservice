package com.example.tasklistusermicroservice.web.dto.auth;

import jakarta.validation.constraints.NotNull;
public class JwtRequest {
    @NotNull(message = "Username must be not null!")
    private String username;
    @NotNull(message = "Password must be not null!")
    private String password;


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
}
