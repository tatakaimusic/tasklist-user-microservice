package com.example.tasklistusermicroservice.web.dto.mapper;

import com.example.tasklistusermicroservice.model.user.User;
import com.example.tasklistusermicroservice.web.dto.UserDTO;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface UserMapper extends Mappable<User, UserDTO> {
}
