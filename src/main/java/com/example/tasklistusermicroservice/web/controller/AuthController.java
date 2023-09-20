package com.example.tasklistusermicroservice.web.controller;

import com.example.tasklistusermicroservice.model.user.User;
import com.example.tasklistusermicroservice.service.UserService;
import com.example.tasklistusermicroservice.web.dto.UserDTO;
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

    @Autowired
    public AuthController(UserMapper userMapper, UserService userService) {
        this.userMapper = userMapper;
        this.userService = userService;
    }

    @PostMapping("/register")
    public UserDTO register(@Validated @RequestBody UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);
        return userMapper.toDTO(userService.create(user));
    }
}
