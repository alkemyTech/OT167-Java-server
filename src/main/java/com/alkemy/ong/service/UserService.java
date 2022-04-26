package com.alkemy.ong.service;

import com.alkemy.ong.dto.UserDto;
import com.alkemy.ong.model.Role;
import com.alkemy.ong.security.model.UserEntity;
import java.util.List;
import java.util.Optional;

public interface UserService {


    UserEntity findByEmail(String username);

    List<UserEntity> getUsers();

    Role getRole(String name);

    List<UserDto> getAllUsers();

    Optional<UserEntity> findUserById(Long id);

    Optional<UserEntity> findById(Long id);

    void delete(Long id);

    void setRole(Long id);

}

