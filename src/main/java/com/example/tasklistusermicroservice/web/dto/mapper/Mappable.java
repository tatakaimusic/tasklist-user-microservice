package com.example.tasklistusermicroservice.web.dto.mapper;

public interface Mappable<E, D> {
    D toDTO(E entity);

    E toEntity(D dto);
}
