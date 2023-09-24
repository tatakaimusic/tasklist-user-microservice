package com.example.tasklistusermicroservice.service;

import com.example.tasklistusermicroservice.web.dto.auth.JwtRequest;
import com.example.tasklistusermicroservice.web.dto.auth.JwtResponse;

public interface AuthService {

    JwtResponse login(JwtRequest loginRequest);

    JwtResponse refresh(String refreshToken);
}
