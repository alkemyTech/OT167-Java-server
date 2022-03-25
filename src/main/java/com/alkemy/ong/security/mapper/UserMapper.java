package com.alkemy.ong.security.mapper;

import com.alkemy.ong.dto.UserDto;
import com.alkemy.ong.dto.UserDtoCreator;
import com.alkemy.ong.dto.UserRegisterRequest;
import com.alkemy.ong.dto.UserRegisterResponse;
import com.alkemy.ong.model.Role;
import org.springframework.stereotype.Component;
import com.alkemy.ong.model.User;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@Component
public class UserMapper {
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public UserDto convertUserToDto (User user){
        UserDto userDto = new UserDto();
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setPhoto(user.getPhoto());
        return userDto;
    }

    public User UserDtoToEntity(UserDtoCreator dto) {
        User user= new User();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setPhoto(dto.getPhoto());
        return user;
    }
    
    
    public User userRegisterRequestDto2User(UserRegisterRequest userDto) {
       
        if ( userDto == null ) {
            return null;
        }
        List<Role> role = new ArrayList<>();
        role.add(userDto.getRol());

        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setPhoto(userDto.getPhoto());
        user.setCreationDate(LocalDate.now());
        user.setRoles(role);

        return user;
    }
    
    public UserRegisterResponse user2UserRegisterResponseDto(User user) {
        if ( user == null ) {
            return null;
        }
        UserRegisterResponse user1 = new UserRegisterResponse();
        user1.setId(user.getId());
        user1.setFirstName(user.getFirstName());
        user1.setLastName(user.getLastName());
        user1.setEmail(user.getEmail());
        return user1;
        
        
    }

    
  
    
}