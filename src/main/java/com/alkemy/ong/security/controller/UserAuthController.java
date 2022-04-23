package com.alkemy.ong.security.controller;

import com.alkemy.ong.dto.*;
import com.alkemy.ong.exception.DataAlreadyExistException;
import com.alkemy.ong.security.dto.UserLoginRequest;
import com.alkemy.ong.security.dto.UserLoginResponse;
import com.alkemy.ong.security.dto.UserRegisterRequest;
import com.alkemy.ong.security.dto.UserRegisterResponse;
import com.alkemy.ong.security.service.UserDetailsCustomService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;

@Tag(name = "Authentication")
@Controller
@RequestMapping("/auth")
public class UserAuthController {

    @Autowired
    private UserDetailsCustomService userDetailsCustomService;

    @Operation(summary = "User Data Fetching")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Succesfull User Data Fetching",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))}),
            @ApiResponse(responseCode = "403", description = "Unsuccessful User Data Fetching, you do not have the permissions to enter", content = @Content)
    })
    @GetMapping("/me")
    public ResponseEntity<UserDto> userData(HttpServletRequest request) {
        
        return new ResponseEntity<>(userDetailsCustomService.userDataFetching(request), HttpStatus.ACCEPTED);
    }

    @Operation(summary = "User login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Succesfull login",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = UserLoginResponse.class))}),
            @ApiResponse(responseCode = "403", description = "Unsuccesfull login, you do not have the permissions to enter", content = @Content)
    })
    @PostMapping("/login")
    public ResponseEntity<UserRegisterResponse> logIn(@Valid @RequestBody UserLoginRequest userDto){
        
        return new ResponseEntity<>(userDetailsCustomService.logIn(userDto), HttpStatus.ACCEPTED);
    }

    @Operation(summary = "Register a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registration successful",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = UserRegisterResponse.class))}),
            @ApiResponse(responseCode = "406", description = "Email already in use by another user", content = @Content)
    })
    @PostMapping("/register")
    public ResponseEntity<UserRegisterResponse> registerUser(@Valid @RequestBody UserRegisterRequest userReq) throws DataAlreadyExistException, IOException {
        return new ResponseEntity<>(userDetailsCustomService.register(userReq), HttpStatus.CREATED);
    }
}
