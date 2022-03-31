package com.alkemy.ong.security.dto;


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
    private String jwt;

}
