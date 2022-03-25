package com.alkemy.ong.security.dto;

import com.alkemy.ong.security.model.UserEntity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    

}
