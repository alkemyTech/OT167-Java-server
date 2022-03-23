package com.alkemy.ong.dto;

import com.alkemy.ong.model.User;
import lombok.*;

//@RequiredArgsConstructor
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private String firstName;
    private String lastName;
    private String email;
    private String photo;


}
