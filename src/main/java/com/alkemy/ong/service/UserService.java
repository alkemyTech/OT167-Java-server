package com.alkemy.ong.service;

import com.alkemy.ong.model.Role;
import com.alkemy.ong.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.alkemy.ong.model.User;
import com.alkemy.ong.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    public User findByUsername(String email) {
        return userRepository.findByEmail(email);
    }

    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public User findByEmail(String email)
    {
        return userRepository.findByEmail(email);
    }

    public Role getRole(String name) {

        return roleRepository.findByName(name);
    }
}
