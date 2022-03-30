package com.alkemy.ong.service;

import com.alkemy.ong.dto.UserDto;
import com.alkemy.ong.exception.NotFoundException;
import com.alkemy.ong.model.Role;
import com.alkemy.ong.security.dto.UserRegisterResponse;
import com.alkemy.ong.security.model.UserEntity;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserService {

    UserRegisterResponse findByEmail(UserEntity user) throws NotFoundException;

    UserEntity findByEmail(String username);

    List<UserEntity> getUsers();

    Role getRole(String name);

    List<UserDto> getAllUsers();

    public UserRegisterResponse register(UserRegisterRequest userReq) throws DataAlreadyExistException, IOException;

    Optional<UserEntity> findUserById(Long id);
}