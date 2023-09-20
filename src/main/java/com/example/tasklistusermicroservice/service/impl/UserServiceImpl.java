package com.example.tasklistusermicroservice.service.impl;

import com.example.tasklistusermicroservice.model.exception.UserNotFoundException;
import com.example.tasklistusermicroservice.model.user.User;
import com.example.tasklistusermicroservice.repository.UserRepository;
import com.example.tasklistusermicroservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
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
    public User update(User user) {
        return userRepository.save(user);
    }

    @Override
    public User create(User user) {
        return userRepository.save(user);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
