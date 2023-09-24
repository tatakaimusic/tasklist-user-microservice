package com.example.tasklistusermicroservice.service;

import com.example.tasklistusermicroservice.model.Notification;
import com.example.tasklistusermicroservice.web.dto.UserDTO;

public interface KafkaNotificationService {

    void send(Notification notification, UserDTO user);
}
