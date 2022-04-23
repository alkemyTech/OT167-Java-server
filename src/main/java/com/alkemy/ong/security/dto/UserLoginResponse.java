package com.alkemy.ong.security.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginResponse {

    @Schema(name = "id", type = "Long", example = "9")
    private Long id;
    @Schema(name = "firstName", example = "Gabriel", type = "String")
    private String firstName;
    @Schema(name = "lastName", example = "Rosa", type = "String")
    private String lastName;
    @Schema(name = "email", example = "gabriel@mail.com", type = "String")
    private String email;
    @Schema(name = "jwt", example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0YXJhemFnYS5taWNrYWVsYUBnbWFpbC5jb20iLCJleHAiOjE2NDg3NzMyNTgsImlhdCI6MTY0ODczNzI1OH0.yA07ci-rUbAvfoIAB4jeqQwl8lg1NOabLLcCZFLZJZV4", type = "String")
    private String jwt;

}
