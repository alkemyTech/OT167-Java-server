package com.alkemy.ong.auth.service;

import com.alkemy.ong.auth.dto.UserRequestDTO;
import com.alkemy.ong.auth.model.UserEntity;
import com.alkemy.ong.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserDetailsCustomService {

    @Autowired
    UserRepository userRepository;

    public UserEntity authenticateUser(UserRequestDTO userRequest) throws Exception{

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        UserEntity userEntity = userRepository.findByEmail();

        if(!(passwordEncoder.matches(userEntity.getPassword(), userRequest.getPassword()))){
            throw new Exception("Incorrect username or password");
        }
        return userEntity;
    }
}
