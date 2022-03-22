package com.alkemy.ong.auth.service;

import com.alkemy.ong.auth.dto.UserRequestDTO;
import com.alkemy.ong.auth.repository.UserRepository;
import com.alkemy.ong.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import com.alkemy.ong.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User authenticateUser(UserRequestDTO userRequest) throws Exception{

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        User userEntity = userRepository.findByEmail(userRequest.getEmail());

        if(!(passwordEncoder.matches(userEntity.getPassword(), userRequest.getPassword()))){
            throw new NotFoundException("no users found with that mail, try again");
        }
        return userEntity;
    }

    public boolean save(UserRequestDTO userRequestDTO) {

        User userEntity = new User();
        userEntity.setEmail(userRequestDTO.getEmail());
        userEntity.setPassword(userRequestDTO.getPassword());
        userEntity = userRepository.save(userEntity);
        /*if(userEntity != null){
            emailService.sendWelcomeEmailTo(userEntity.getUsername());
        }*/
        return userEntity != null;

    }
}
