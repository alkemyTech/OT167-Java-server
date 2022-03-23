package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.UserRegisterRequest;
import com.alkemy.ong.dto.UserRegisterResponse;
import com.alkemy.ong.exception.DataAlreadyExistException;
import com.alkemy.ong.mapper.UserMapper;
import com.alkemy.ong.model.User;
import com.alkemy.ong.repository.UserRepository;
import com.alkemy.ong.service.UserService;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private MessageSource messageSource;
  
    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserRegisterResponse register(UserRegisterRequest userReq) throws DataAlreadyExistException {

        if (this.findByEmail(userReq.getEmail()) != null) {
            throw new DataAlreadyExistException(messageSource.getMessage("email.already.exist",null, Locale.ENGLISH));
        }
        User user = userMapper.userRegisterRequestDto2User(userReq);
        User userSaved = userRepository.save(user);
        return userMapper.user2UserRegisterResponseDto(userSaved);
        
        
    }

}
