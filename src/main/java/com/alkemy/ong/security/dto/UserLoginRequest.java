package com.alkemy.ong.security.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UserLoginRequest {

    @Schema(example = "gabriel@mail.com", required = true)
    @NotBlank(message = "Email can't be blank")
    @Email(message = "Email should be valid.", regexp = "[A-Za-z0-9._%-+]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}")
    private String email;

    @Schema(name = "password", example = "12345678", type = "String", required = true)
    @NotBlank(message = "Password is mandatory.")
    @Size(min = 6, message = "The password must be at least 6 characters")
    private String password;

}
