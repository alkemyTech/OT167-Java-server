package com.alkemy.ong.security.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterResponse {

    @Schema(name = "id", type = "Long", example = "1")
    private Long id;
    @Schema(name = "firstName", example = "Mickaela", type = "String")
    private String firstName;
    @Schema(name = "lastName", example = "Tarazaga", type = "String")
    private String lastName;
    @Schema(name = "email", example = "mickaela@mail.com", type = "String")
    private String email;
    @Schema(name = "jwt", example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0YXJhemFnYS5taWNrYWVsYUBnbWFpbC5jb20iLCJleHAiOjE2NDg3NzMyNTgsImlhdCI6MTY0ODczNzI1OH0.yA07ci-rUbAvfoIAB4jeqQwl8lg1NOabLLcCZFLZuis", type = "String")
    private String jwt;

}
