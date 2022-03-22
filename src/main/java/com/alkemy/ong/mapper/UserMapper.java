package com.alkemy.ong.mapper;

import com.alkemy.ong.dto.UserDto;
import com.alkemy.ong.dto.UserDtoCreator;
import com.alkemy.ong.dto.UserRegisterRequest;
import com.alkemy.ong.dto.UserRegisterResponse;
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
    public List<UserDto> UserDtoList (List<User> user){
        List<UserDto> usrDtoList = new ArrayList<>();
        user.forEach(usr -> usrDtoList.add(convertUserToDto(usr)));
        return usrDtoList;
    }
    public User createUser(UserDtoCreator dto) {
        return new User(null,Boolean.FALSE,dto.getFirstName(),dto.getLastName(),dto.getEmail(),dto.getPassword(),dto.getPassword(),dto.getPhoto(),null,null,null);
    }
    
    
    public User UserRegisterDto2User(UserRegisterRequest userDto) {
       
        if ( userDto == null ) {
            return null;
        }
        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setPhoto(userDto.getPhoto());
        return user;
    }
    
    public UserRegisterResponse User2UserRegisterDto(User user) {
        if ( user == null ) {
            return null;
        }
        UserRegisterResponse userDto = new UserRegisterResponse();
        userDto.setId(user.getId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        return userDto;
    }

    
  
    
}