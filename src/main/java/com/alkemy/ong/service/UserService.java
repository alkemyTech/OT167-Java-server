package com.alkemy.ong.service;

import com.alkemy.ong.dto.UserDto;
import com.alkemy.ong.exception.DataAlreadyExistException;
import com.alkemy.ong.exception.NotFoundException;
import com.alkemy.ong.model.Role;
import com.alkemy.ong.security.dto.UserRegisterRequest;
import com.alkemy.ong.security.dto.UserRegisterResponse;
import com.alkemy.ong.security.model.UserEntity;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface UserService {

    UserRegisterResponse findByEmail(UserEntity user) throws NotFoundException;

    UserEntity findByEmail(String username);

    List<UserEntity> getUsers();

    Role getRole(String name);

    List<UserDto> getAllUsers();

    Optional<UserEntity> findUserById(Long id);

    void delete(Long id);
}

