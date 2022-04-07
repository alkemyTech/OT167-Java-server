package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.UserDto;
import com.alkemy.ong.exception.NotFoundException;
import com.alkemy.ong.model.Role;
import com.alkemy.ong.repository.RoleRepository;
import com.alkemy.ong.repository.UserRepository;
import com.alkemy.ong.security.dto.UserRegisterResponse;
import com.alkemy.ong.security.mapper.UserMapper;
import com.alkemy.ong.service.EmailService;
import com.alkemy.ong.security.model.UserEntity;
import com.alkemy.ong.security.service.JwtUtils;
import com.alkemy.ong.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private EmailService emailService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;

    @Override
    public List<UserEntity> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserRegisterResponse findByEmail(UserEntity user) throws NotFoundException {

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        UserEntity userFound = this.findByEmail(user.getEmail());

        if(!(passwordEncoder.matches(user.getPassword(),userFound.getPassword()))){
            throw new NotFoundException(messageSource.getMessage("password.not.same",null, Locale.ENGLISH));
        }
        return userMapper.user2UserRegisterResponseDto(userFound, jwtUtils.generateJwt(userFound));
    }

    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<UserEntity> findUserById(Long id) {
        return Optional.ofNullable(userRepository.findById(id).orElseThrow(() -> new NotFoundException(messageSource.getMessage("user.not.found",null, Locale.ENGLISH))));
    }

    @Override
    public UserEntity findById(Long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public Role getRole(String name) {

        return (Role) roleRepository.findByName(name);
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream().map(user -> userMapper.convertUserToDto(user)).collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
       Optional<UserEntity> user = findUserById(id);
        user.get().setDeleted(Boolean.TRUE);
        userRepository.save(user.get());    
    }

}