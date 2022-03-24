package com.alkemy.ong.service;

import com.alkemy.ong.dto.UserDto;
import com.alkemy.ong.dto.UserRegisterRequest;
import com.alkemy.ong.dto.UserRegisterResponse;
import com.alkemy.ong.exception.DataAlreadyExistException;
import com.alkemy.ong.exception.NotFoundException;
import com.alkemy.ong.model.Role;
import com.alkemy.ong.model.User;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface UserService {

    User findByEmail(User user) throws NotFoundException;

    User findByEmail(String username);

    List<User> getUsers();

    Role getRole(String name);

    List<UserDto> getAllUsers();

    public UserRegisterResponse register(UserRegisterRequest userReq) throws DataAlreadyExistException, IOException;

    Optional<User> findUserById(Long id);
}


