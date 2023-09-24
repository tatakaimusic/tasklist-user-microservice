package com.example.tasklistusermicroservice.service.impl;

import com.example.tasklistusermicroservice.model.Notification;
import com.example.tasklistusermicroservice.model.user.User;
import com.example.tasklistusermicroservice.service.KafkaNotificationService;
import com.example.tasklistusermicroservice.web.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderRecord;

@Service
public class KafkaNotificationServiceImpl implements KafkaNotificationService {

    private final KafkaSender<String, Object> kafkaSender;

    @Autowired
    public KafkaNotificationServiceImpl(KafkaSender<String, Object> kafkaSender) {
        this.kafkaSender = kafkaSender;
    }

    @Override
    public void send(Notification notification, UserDTO user) {
        String topic = switch (notification) {
            case REGISTRATION -> "notification-registration";
            case REMINDER -> "notification-reminder";
        };
        kafkaSender.send(
                        Mono.just(
                                SenderRecord.create(topic,
                                        0,
                                        System.currentTimeMillis(),
                                        String.valueOf(user.hashCode()),
                                        user,
                                        null)
                        )
                )
                .subscribe();
    }
}
