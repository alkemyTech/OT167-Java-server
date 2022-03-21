package com.alkemy.ong.controller;

import com.alkemy.ong.dto.UserMapper;
import com.alkemy.ong.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth/me")
public class UserAuth {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> userData(@PathVariable Long id){
        return ResponseEntity.ok(userMapper.convertUserToDto(userService.findById(id)));
    }
}
