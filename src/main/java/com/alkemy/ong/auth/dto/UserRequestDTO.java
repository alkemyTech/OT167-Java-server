package com.alkemy.ong.auth.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter @Setter
public class UserRequestDTO {

    @NotNull(message = "user mail cannot be empty")
    private String email;

    @NotNull(message = "password cannot be empty")
    private String password;

}
