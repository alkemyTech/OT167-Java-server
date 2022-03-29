package com.alkemy.ong.service;

import com.alkemy.ong.dto.UserDto;
import com.alkemy.ong.exception.NotFoundException;
import com.alkemy.ong.model.Role;
import com.alkemy.ong.security.model.UserEntity;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

public interface UserService {

    UserEntity loginUser(UserEntity user) throws NotFoundException;

    UserEntity loginUser(String username);

    List<UserEntity> getUsers();

    Role getRole(String name);

    List<UserDto> getAllUsers();

    Optional<UserEntity> findUserById(Long id);
}


