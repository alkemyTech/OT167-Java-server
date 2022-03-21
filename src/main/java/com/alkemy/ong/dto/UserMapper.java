package com.alkemy.ong.dto;

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
        userDto.getPhoto(user.getPhoto());
        return userDto;
    }
    public List<UserDto> UserDtoList (List<User> user){
        List<UserDto> usrDtoList = new ArrayList<>();
        user.forEach(usr -> usrDtoList.add(convertUserToDto(usr)));
        return usrDtoList;
    }
    public User createUser(UserDtoCreator dto) {
        return new User(null,Boolean.FALSE,dto.getFirstName(),dto.getLastName(),dto.getEmail(),dto.getPassword(),dto.getPassword(),dto.getPhoto(),null,null,null);
    }
}