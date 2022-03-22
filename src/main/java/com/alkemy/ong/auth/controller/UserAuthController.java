package com.alkemy.ong.auth.controller;

import com.alkemy.ong.auth.dto.UserRequestDTO;
import com.alkemy.ong.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.alkemy.ong.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/auth")
public class UserAuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/singup")
    public ResponseEntity<?> singUp(@Valid @RequestBody UserRequestDTO userRequestDTO){

        userService.save(userRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/login")
    //recive un mail y una contraseña, y devuelve un jwt
    public ResponseEntity<User> logIn(@Valid @RequestBody UserRequestDTO userRequest) throws Exception {
        /*toma el mail de usuario y contraseña y los autentica, UserDetails es una libreria de spring*/
        User userEntity = userService.authenticateUser(userRequest);

        return ResponseEntity.ok(userEntity);
    }
}
