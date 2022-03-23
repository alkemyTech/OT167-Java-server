package com.alkemy.ong.service.impl;

import com.alkemy.ong.exception.NotFoundException;
import com.alkemy.ong.model.User;
import com.alkemy.ong.repository.UserRepository;
import com.alkemy.ong.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;


    public User findByUsername(String username) {
        return userRepository.findByFirstName(username);
    }

    @Override
    public User findByEmail(User user) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        User userFound = userRepository.findByEmail(user.getEmail());

        if(!(passwordEncoder.matches(userFound.getPassword(), user.getPassword()))){
            throw new NotFoundException("no users found with that mail, try again");
        }
        return userFound;
    }
}
