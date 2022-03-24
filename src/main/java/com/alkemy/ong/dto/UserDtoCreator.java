package com.alkemy.ong.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter @Setter
public class UserDtoCreator {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String photo;
}
