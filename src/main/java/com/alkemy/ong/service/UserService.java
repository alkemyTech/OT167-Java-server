package com.alkemy.ong.service;

import org.springframework.beans.factory.annotation.Autowired;
import com.alkemy.ong.model.User;
import com.alkemy.ong.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User findByUsername(String username) {
        return userRepository.findByUsername();
    }
}
