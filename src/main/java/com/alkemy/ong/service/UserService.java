package com.alkemy.ong.service;

import com.alkemy.ong.dto.UserDto;
import com.alkemy.ong.model.Role;
import com.alkemy.ong.model.User;

import java.util.List;

public interface UserService {
    public User findByUsername(String email);

    public List<User> getUsers();

    public User findByEmail(String email);

    public Role getRole(String name);

    public List<UserDto> getAllUsers();

}
