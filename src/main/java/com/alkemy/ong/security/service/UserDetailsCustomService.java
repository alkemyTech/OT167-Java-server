package com.alkemy.ong.security.service;
import com.alkemy.ong.security.model.UserEntity;
import com.alkemy.ong.exception.DataAlreadyExistException;
import com.alkemy.ong.repository.UserRepository;
import com.alkemy.ong.security.dto.UserRegisterRequest;
import com.alkemy.ong.security.dto.UserRegisterResponse;
import com.alkemy.ong.security.mapper.UserMapper;
import com.alkemy.ong.service.UserService;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Locale;
import org.springframework.context.MessageSource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

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

    public UserRegisterResponse register(UserRegisterRequest userReq) throws DataAlreadyExistException {

        if (userService.findByEmail(userReq.getEmail()) != null) {
            throw new DataAlreadyExistException(messageSource.getMessage("email.already.exist",null, Locale.ENGLISH));
        }
        UserEntity user = userMapper.userRegisterRequestDto2User(userReq);
        UserEntity userSaved = userRepository.save(user);
        return userMapper.user2UserRegisterResponseDto(userSaved);
             
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(email);
        User userDet = new User(user.getEmail(), user.getPassword(), Collections.emptyList()); 
        return userDet;}

 }





