package com.alkemy.ong.security.mapper;

import com.alkemy.ong.dto.UserDto;
import com.alkemy.ong.dto.UserDtoCreator;
import org.springframework.stereotype.Component;
import com.alkemy.ong.model.User;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserMapper {
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
}