package com.alkemy.ong.service.impl;

import com.alkemy.ong.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import com.alkemy.ong.model.User;
import com.alkemy.ong.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public User findByUsername(String email) {
        return userRepository.findByEmail(email);
    }

}
