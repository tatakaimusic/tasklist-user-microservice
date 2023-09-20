package com.example.tasklistusermicroservice.service;

import com.example.tasklistusermicroservice.model.user.User;

public interface UserService {

    User getById(Long id);

    User getByEmail(String email);

    User update(User user);

    User create(User user);

    void delete(Long id);
}
