package com.alkemy.ong.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationResponse {
    @Schema(name = "jwt",example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0YXJhemFnYS5taWNrYWVsYUBnbWFpbC5jb20iLCJleHAiOjE2NDg3NzMyNTgsImlhdCI6MTY0ODczNzI1OH0.yA07ci-rUbAvfoIAB4jeqQwl8lg1NOabLLcCZFLZuis"
            , type = "String",description = "description: json web token for login the user")
    private String jwt;

    public AuthenticationResponse(String jwt) {
        this.jwt = jwt;
    }
}
