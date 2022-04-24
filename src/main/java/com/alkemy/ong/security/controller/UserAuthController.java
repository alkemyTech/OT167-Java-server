package com.alkemy.ong.security.controller;

import com.alkemy.ong.dto.*;
import com.alkemy.ong.exception.DataAlreadyExistException;
import com.alkemy.ong.exception.MessageInfo;
import com.alkemy.ong.exception.MessageResponse;
import com.alkemy.ong.exception.MessagesInfo;
import com.alkemy.ong.security.dto.UserLoginRequest;
import com.alkemy.ong.security.dto.UserRegisterRequest;
import com.alkemy.ong.security.dto.UserRegisterResponse;
import com.alkemy.ong.security.service.UserDetailsCustomService;
import com.alkemy.ong.service.RoleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;

@Tag(name = "Authentication")
@Controller
@RequestMapping("/auth")
public class UserAuthController {
    @Autowired
    private MessageResponse messageResponse;
    @Autowired
    private UserDetailsCustomService userDetailsCustomService;

    @GetMapping("/me")
    public ResponseEntity<?> userData(HttpServletRequest request) {
        
        return new ResponseEntity<>(userDetailsCustomService.userDataFetching(request), HttpStatus.ACCEPTED);
    }

    @Operation(description = "User login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Succesfull login",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))}),
            @ApiResponse(responseCode = "403", description = "Unsuccesfull login", content = @Content)
    })
    @PostMapping("/login")
    public ResponseEntity<UserRegisterResponse> logIn(@Valid @RequestBody UserLoginRequest userDto){
        
        return new ResponseEntity<>(userDetailsCustomService.logIn(userDto), HttpStatus.ACCEPTED);
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
    @PostMapping("/addRole/{id}")
    public ResponseEntity<MessageInfo> addRoleUser(@PathVariable Long id, @RequestBody AddRoleToUserForm roleName, WebRequest request) {
        userDetailsCustomService.addRoleToUser(id, roleName.getRoleName());
        return ResponseEntity.status(HttpStatus.OK).body(messageResponse.messageOk("user add a new role how " + roleName.getRoleName(), HttpStatus.OK.value(), request));
    }
    @PostMapping("/updateRolUser/{id}")
    public ResponseEntity<MessageInfo> updateRoleUser(@PathVariable Long id, @RequestBody AddRoleToUserForm roleName, WebRequest request) {
        userDetailsCustomService.updateRoleToUser(id, roleName.getRoleName());
        return ResponseEntity.status(HttpStatus.OK).body(messageResponse.messageOk("user update a new role how " + roleName.getRoleName(), HttpStatus.CREATED.value(), request));
    }
    @GetMapping("/accessdenied")
    public ResponseEntity<MessageInfo> accesDenied (WebRequest request){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(messageResponse.messageOk("Cannot access the resource. Login first.", HttpStatus.FORBIDDEN.value(), request));
    }
}
@Data
class AddRoleToUserForm{
    private String roleName;
}
