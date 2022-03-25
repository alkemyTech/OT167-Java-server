package com.alkemy.ong.security.service;
import com.alkemy.ong.security.model.UserEntity;
import com.alkemy.ong.exception.DataAlreadyExistException;
import org.springframework.security.core.userdetails.UserDetails;

import com.alkemy.ong.repository.UserRepository;
import com.alkemy.ong.security.dto.UserRegisterRequest;
import com.alkemy.ong.security.dto.UserRegisterResponse;
import com.alkemy.ong.security.mapper.UserMapper;
import com.alkemy.ong.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;
import java.util.stream.Collectors;
import org.springframework.context.MessageSource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;

@Service
public class UserDetailsCustomService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private UserService userService;
            
    @Autowired
    private MessageSource messageSource;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        return new User("foo", "foo", new ArrayList<>());
    }
    
        
    public UserRegisterResponse register(UserRegisterRequest userReq) throws DataAlreadyExistException {

        if (userService.findByEmail(userReq.getEmail()) != null) {
            throw new DataAlreadyExistException(messageSource.getMessage("email.already.exist",null, Locale.ENGLISH));
        }
        UserEntity user = userMapper.userRegisterRequestDto2User(userReq);
        UserEntity userSaved = userRepository.save(user);
        return userMapper.user2UserRegisterResponseDto(userSaved);
             
    }


}





