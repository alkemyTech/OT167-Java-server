package com.alkemy.ong.controller;

import com.alkemy.ong.dto.UserDto;
import com.alkemy.ong.security.mapper.UserMapper;
import com.alkemy.ong.service.UserService;
import com.fasterxml.jackson.databind.introspect.TypeResolutionContext;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;

    @DeleteMapping("/{id}")
    public ResponseEntity<TypeResolutionContext.Empty> delete(@PathVariable Long id) throws EntityNotFoundException {
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping()
    public ResponseEntity<List<UserDto>> getAllUsersD() {
        return new ResponseEntity<List<UserDto>>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findUserById(@PathVariable Long id) {
        return ResponseEntity.ok().body(userMapper.convertUserToDto(userService.findUserById(id).get()));
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> assignRoleAdmin(@PathVariable Long id){
        userService.setRole(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
