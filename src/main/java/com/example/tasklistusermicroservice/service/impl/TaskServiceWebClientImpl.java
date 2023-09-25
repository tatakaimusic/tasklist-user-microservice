package com.example.tasklistusermicroservice.service.impl;

import com.example.tasklistusermicroservice.service.TaskServiceWebClient;
import com.example.tasklistusermicroservice.web.dto.TaskDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class TaskServiceWebClientImpl implements TaskServiceWebClient {

    private final WebClient webClient;

    @Autowired
    public TaskServiceWebClientImpl(WebClient webClient) {
        this.webClient = webClient;
    }


    @Override
    public Mono<TaskDTO> getById(Long id) {
        return webClient
                .get()
                .uri(String.join("", "api/v1/tasks/{id}", id.toString()))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(TaskDTO.class);
    }

    @Override
    public Mono<List<TaskDTO>> getAllByUserEmail(String userEmail) {
        return webClient
                .get()
                .uri(String.join("/api/v1/tasks/{email}", userEmail))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<TaskDTO>>() {
                });
    }

    @Override
    public Mono<TaskDTO> create(TaskDTO taskDTO) {
        Mono<TaskDTO> taskDTOMono = Mono.just(taskDTO);
        return webClient
                .post()
                .uri(String.join("/api/v1/tasks"))
                .contentType(MediaType.APPLICATION_JSON)
                .body(taskDTOMono, TaskDTO.class)
                .retrieve()
                .bodyToMono(TaskDTO.class);
    }

    @Override
    public Mono<TaskDTO> update(TaskDTO taskDTO) {
        Mono<TaskDTO> taskDTOMono = Mono.just(taskDTO);
        return webClient
                .put()
                .uri(String.join("/api/v1/tasks"))
                .contentType(MediaType.APPLICATION_JSON)
                .body(taskDTOMono, TaskDTO.class)
                .retrieve()
                .bodyToMono(TaskDTO.class);
    }

    @Override
    public Mono<Void> delete(Long id) {
        return webClient.method(HttpMethod.DELETE)
                .uri("/api/v1/tasks/{id}", id)
                .retrieve()
                .bodyToMono(Void.class);
    }
}
