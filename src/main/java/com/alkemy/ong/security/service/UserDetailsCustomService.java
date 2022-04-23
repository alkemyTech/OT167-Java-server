package com.alkemy.ong.security.service;
import com.alkemy.ong.dto.RoleDto;
import com.alkemy.ong.dto.UserDto;
import com.alkemy.ong.exception.BadRequestException;
import com.alkemy.ong.mapper.RoleMapper;
import com.alkemy.ong.model.Role;
import com.alkemy.ong.repository.RoleRepository;
import com.alkemy.ong.security.model.UserEntity;
import com.alkemy.ong.exception.DataAlreadyExistException;
import com.alkemy.ong.exception.NotFoundException;
import com.alkemy.ong.repository.UserRepository;
import com.alkemy.ong.security.dto.UserLoginRequest;
import com.alkemy.ong.security.dto.UserRegisterRequest;
import com.alkemy.ong.security.dto.UserRegisterResponse;
import com.alkemy.ong.security.mapper.UserMapper;
import com.alkemy.ong.service.UserService;
import java.util.Collection;

import com.alkemy.ong.service.impl.EmailServiceImpl;
import com.amazonaws.services.cognitoidentity.model.RoleMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.MessageSource;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import org.springframework.http.ResponseEntity;

@Service
public class UserDetailsCustomService implements UserDetailsService {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleMapper roleMapping;
    
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

    public UserRegisterResponse logIn(UserLoginRequest userLoginRequest) throws NotFoundException {

        if (userService.findByEmail(userLoginRequest.getEmail()) == null) {
            throw new NotFoundException(messageSource.getMessage("email.not.found",null, Locale.ENGLISH));
        }
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        UserEntity user = userMapper.UserDtoToEntity(userLoginRequest);
        UserEntity userFound = userService.findByEmail(user.getEmail());
        if(!(passwordEncoder.matches(user.getPassword(),userFound.getPassword()))){
            throw new NotFoundException(messageSource.getMessage("password.not.same",null, Locale.ENGLISH));
        }
        return userMapper.user2UserRegisterResponseDto(userFound, jwtUtils.generateJwt(userFound));
    }
    
    public UserDto userDataFetching(HttpServletRequest request) throws NotFoundException {

        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            username = jwtUtils.extractUsername(jwt);
        }

        return userMapper.convertUserToDto(userService.findByEmail(username));
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
    public void addRoleToUser(Long id, String rolName) {
        Optional<UserEntity> user = Optional.ofNullable(userRepository.findById(id).orElseThrow(()-> new NotFoundException(messageSource.getMessage("user.not.found",null, Locale.ENGLISH))));
        Role roleFound = Optional.ofNullable(roleRepository.findByName(rolName)).orElseThrow(()-> new NotFoundException(messageSource.getMessage("role.not.found",null, Locale.ENGLISH)));
        if(!user.get().getRoles().isEmpty()) throw new BadRequestException(messageSource.getMessage("user.has.role",null, Locale.ENGLISH));
        user.get().getRoles().add(roleFound);
        userRepository.save(user.get());
    }
    public void updateRoleToUser(Long id, String newRoleName){
        Optional<UserEntity> user = Optional.ofNullable(userRepository.findById(id).orElseThrow(()-> new NotFoundException(messageSource.getMessage("user.not.found",null, Locale.ENGLISH))));
        Role roleNew = Optional.ofNullable(roleRepository.findByName(newRoleName)).orElseThrow(()-> new NotFoundException(messageSource.getMessage("role.not.found",null, Locale.ENGLISH)));
        if(user.get().getRoles().stream().anyMatch(role -> role.equals(roleNew))) throw new BadRequestException(messageSource.getMessage("user.has.that.role", new Object[]{newRoleName}, Locale.ENGLISH));
        user.get().getRoles().add(roleNew);
        user.get().getRoles().stream().forEach((r) -> {if(!roleNew.equals(r)) user.get().getRoles().remove(r);});
        userRepository.save(user.get());
    }
    public void saveRole(Role role) {
        roleRepository.save(role);
    }
    public void updateRole(Long id, RoleDto roleDto) {
        Optional<Role> role = Optional.ofNullable(roleRepository.findById(id).orElseThrow(()-> new NotFoundException("role no encontrado")));
        roleDto.setId(roleDto.getId());
        roleRepository.save(roleMapping.createRole(roleDto));
    }
 }





