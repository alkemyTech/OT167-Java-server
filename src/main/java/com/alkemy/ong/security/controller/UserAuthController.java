package com.alkemy.ong.security.controller;

import com.alkemy.ong.dto.*;
import com.alkemy.ong.exception.DataAlreadyExistException;
import com.alkemy.ong.security.dto.UserLoginRequest;
import com.alkemy.ong.security.dto.UserRegisterRequest;
import com.alkemy.ong.security.dto.UserRegisterResponse;
import com.alkemy.ong.security.mapper.UserMapper;
import com.alkemy.ong.security.model.UserEntity;
import com.alkemy.ong.security.service.JwtUtils;
import com.alkemy.ong.security.service.UserDetailsCustomService;
import com.alkemy.ong.service.UserService;
import com.fasterxml.jackson.databind.introspect.TypeResolutionContext.Empty;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import javax.persistence.EntityNotFoundException;

@Tag(name = "Authentication")
@Controller
@RequestMapping("/auth")
public class UserAuthController {

    @Autowired
    private JwtUtils jwtUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailsCustomService userDetailsCustomService;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/me")
    public ResponseEntity<?> userData(HttpServletRequest request) {
        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            username = jwtUtil.extractUsername(jwt);
        }

        return ResponseEntity.ok(userMapper.convertUserToDto(userService.findByEmail(username)));
    }

    @Operation(description = "User login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Succesfull login",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))}),
            @ApiResponse(responseCode = "403", description = "Unsuccesfull login", content = @Content)
    })
    @PostMapping("/login")
    public ResponseEntity<UserRegisterResponse> logIn(@Valid @RequestBody UserLoginRequest userDto){

        UserEntity user = userMapper.UserDtoToEntity(userDto);
        return new ResponseEntity<>(userDetailsCustomService.logIn(user), HttpStatus.ACCEPTED);
    }

    @Operation(description = "Register a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registration successful",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = UserRegisterResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Email already in use by another user", content = @Content)
    })
    @PostMapping("/register")
    public ResponseEntity<UserRegisterResponse> registerUser(@Valid @RequestBody UserRegisterRequest userReq) throws DataAlreadyExistException, IOException {
        return new ResponseEntity<>(userDetailsCustomService.register(userReq), HttpStatus.CREATED);
    }

    

}
