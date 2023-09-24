package com.example.tasklistusermicroservice.web.controller;

import com.example.tasklistusermicroservice.model.user.User;
import com.example.tasklistusermicroservice.service.AuthService;
import com.example.tasklistusermicroservice.service.UserService;
import com.example.tasklistusermicroservice.web.dto.UserDTO;
import com.example.tasklistusermicroservice.web.dto.auth.JwtRequest;
import com.example.tasklistusermicroservice.web.dto.auth.JwtResponse;
import com.example.tasklistusermicroservice.web.dto.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@Validated
public class AuthController {
    private final UserMapper userMapper;

    private final UserService userService;

    private final AuthService authService;

    @Autowired
    public AuthController(UserMapper userMapper, UserService userService, AuthService authService) {
        this.userMapper = userMapper;
        this.userService = userService;
        this.authService = authService;
    }

    @PostMapping("/register")
    public UserDTO register(@Validated @RequestBody UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);
        return userMapper.toDTO(userService.create(user));
    }

    @PostMapping("/login")
    public JwtResponse login(@Validated @RequestBody JwtRequest jwtRequest) {
        return authService.login(jwtRequest);
    }

    @PostMapping("/refresh")
    public JwtResponse refresh(@RequestBody String refreshToken) {
        return authService.refresh(refreshToken);
    }
}
