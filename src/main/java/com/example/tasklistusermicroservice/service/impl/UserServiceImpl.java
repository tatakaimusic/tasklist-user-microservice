package com.example.tasklistusermicroservice.service.impl;

import com.example.tasklistusermicroservice.model.Notification;
import com.example.tasklistusermicroservice.model.exception.UserNotFoundException;
import com.example.tasklistusermicroservice.model.user.User;
import com.example.tasklistusermicroservice.repository.UserRepository;
import com.example.tasklistusermicroservice.service.KafkaNotificationService;
import com.example.tasklistusermicroservice.service.UserService;
import com.example.tasklistusermicroservice.web.dto.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final KafkaNotificationService kafkaNotificationService;

    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, KafkaNotificationService kafkaNotificationService, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.kafkaNotificationService = kafkaNotificationService;
        this.userMapper = userMapper;
    }

    @Override
    public User getById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found!"));
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User with this email not found!"));
    }

    @Override
    @Transactional
    public User update(User user) {
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User create(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalStateException("User with this email already exists!");
        }
        if (!user.getPassword().equals(user.getPasswordConfirmation())) {
            throw new IllegalStateException("Password and password confirmation don't match!");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_USER");
        user = userRepository.save(user);
        kafkaNotificationService.send(Notification.REGISTRATION, userMapper.toDTO(user));
        return user;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
