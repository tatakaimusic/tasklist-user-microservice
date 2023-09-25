package com.example.tasklistusermicroservice.service;

import com.example.tasklistusermicroservice.web.dto.TaskDTO;
import reactor.core.publisher.Mono;

import java.util.List;

public interface TaskServiceWebClient {

    Mono<TaskDTO> getById(Long id);

    Mono<List<TaskDTO>> getAllByUserEmail(String userEmail);

    Mono<TaskDTO> create(TaskDTO taskDTO);

    Mono<TaskDTO> update(TaskDTO taskDTO);

    Mono<Void> delete(Long id);
}
