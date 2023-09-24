package com.example.tasklistusermicroservice.service;

import com.example.tasklistusermicroservice.model.Notification;
import com.example.tasklistusermicroservice.model.user.User;

public interface KafkaNotificationService {

    void send(Notification notification, User user);
}
