package com.alkemy.ong.mapper;

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
    PasswordEncoder passwordEncoder;
    
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
    
    
    public User UserRegisterRequestDto2User(UserRegisterRequest userDto) {
       
        if ( userDto == null ) {
            return null;
        }
        List<Role> rol = new ArrayList<>();
        rol.add(userDto.getRol());

        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setPhoto(userDto.getPhoto());
        user.setCreationDate(LocalDate.now());
        user.setRoles(rol);

        return user;
    }
    
    public UserRegisterResponse User2UserRegisterResponseDto(User user) {
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