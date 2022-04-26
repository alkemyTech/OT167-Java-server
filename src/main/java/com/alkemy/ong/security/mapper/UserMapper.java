package com.alkemy.ong.security.mapper;

import com.alkemy.ong.dto.UserDto;
import com.alkemy.ong.model.Role;
import com.alkemy.ong.security.dto.UserLoginRequest;
import com.alkemy.ong.security.dto.UserRegisterRequest;
import com.alkemy.ong.security.dto.UserRegisterResponse;
import com.alkemy.ong.security.enums.RoleEnum;
import org.springframework.stereotype.Component;
import com.alkemy.ong.security.model.UserEntity;
import com.alkemy.ong.service.RoleService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@Component
public class UserMapper {
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private RoleService roleService;
    
    public UserDto convertUserToDto (UserEntity user){
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setPhoto(user.getPhoto());
        return userDto;
    }

    public UserEntity UserDtoToEntity(UserLoginRequest dto) {
        UserEntity user= new UserEntity();
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        return user;
    }

    public UserEntity userRegisterRequestDto2User(UserRegisterRequest userDto) {
       
        if ( userDto == null ) {
            return null;
        }
        List<Role> roleList = new ArrayList<>();
        roleList.add(roleService.findByName(RoleEnum.USER.getRoleName()));
                     
        UserEntity user = new UserEntity();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setCreationDate(LocalDate.now());
        user.setPhoto(userDto.getPhoto());
        user.setRoles(roleList);

        return user;
    }

    public UserRegisterResponse user2UserRegisterResponseDto(UserEntity user, String jwt) {
        if ( user == null ) {
            return null;
        }
        UserRegisterResponse user1 = new UserRegisterResponse();

        user1.setId(user.getId());
        user1.setFirstName(user.getFirstName());
        user1.setLastName(user.getLastName());
        user1.setEmail(user.getEmail());
        user1.setJwt(jwt);
        return user1;
    }
}