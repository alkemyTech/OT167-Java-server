package com.alkemy.ong.security.service;
import com.alkemy.ong.security.model.UserEntity;
import com.alkemy.ong.exception.DataAlreadyExistException;
import com.alkemy.ong.exception.NotFoundException;
import com.alkemy.ong.repository.UserRepository;
import com.alkemy.ong.security.dto.UserRegisterRequest;
import com.alkemy.ong.security.dto.UserRegisterResponse;
import com.alkemy.ong.security.mapper.UserMapper;
import com.alkemy.ong.service.UserService;
import java.util.Collection;

import com.alkemy.ong.service.impl.EmailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import java.util.Locale;
import java.util.stream.Collectors;

import org.springframework.context.MessageSource;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;

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

    @Autowired
    private EmailServiceImpl emailServiceImpl;

    @Autowired
    private JwtUtils jwtUtils;

    public UserRegisterResponse register(UserRegisterRequest userReq) throws DataAlreadyExistException {

        if (userService.findByEmail(userReq.getEmail()) != null) {
            throw new DataAlreadyExistException(messageSource.getMessage("email.already.exist",null, Locale.ENGLISH));
        }
        UserEntity user = userMapper.userRegisterRequestDto2User(userReq);
        UserEntity userSaved = userRepository.save(user);
        emailServiceImpl.sendWelcomeEmailTo(user);
        String jwt = jwtUtils.generateJwt(userSaved);
        return userMapper.user2UserRegisterResponseDto(userSaved, jwt);
             
    }

    public UserRegisterResponse logIn(UserEntity user) throws NotFoundException {

        if (userService.findByEmail(user.getEmail()) == null) {
            throw new NotFoundException(messageSource.getMessage("email.not.found",null, Locale.ENGLISH));
        }
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        UserEntity userFound = userService.findByEmail(user.getEmail());
        if(!(passwordEncoder.matches(user.getPassword(),userFound.getPassword()))){
            throw new NotFoundException(messageSource.getMessage("password.not.same",null, Locale.ENGLISH));
        }
        return userMapper.user2UserRegisterResponseDto(userFound, jwtUtils.generateJwt(userFound));
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var foundUser = userRepository.findByEmail(username);
        Collection<GrantedAuthority> authorities = foundUser.getRoles().stream()
                .map(roleEntity -> new SimpleGrantedAuthority(roleEntity.getName()))
                .collect(Collectors.toList());
        return new User(
                foundUser.getEmail(),
                foundUser.getPassword(),
                authorities
        );
    }

 }





