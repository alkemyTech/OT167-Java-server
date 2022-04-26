package com.alkemy.ong.controller;

import com.alkemy.ong.dto.UserDto;
import com.alkemy.ong.security.mapper.UserMapper;
import com.alkemy.ong.service.UserService;
import com.fasterxml.jackson.databind.introspect.TypeResolutionContext;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import com.alkemy.ong.exception.MessageInfo;
import com.alkemy.ong.exception.MessageResponse;
import com.alkemy.ong.security.service.UserDetailsCustomService;
import org.springframework.web.context.request.WebRequest;
import static com.alkemy.ong.utils.SwaggerConstants.MODEL_ROL_ERROR_404;
import static com.alkemy.ong.utils.SwaggerConstants.MODEL_ROL_UPDATE;
import java.util.Locale;
import org.springframework.context.MessageSource;

@Tag(name = "User")
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserDetailsCustomService userDetailsCustomService;
    @Autowired
    private MessageResponse messageResponse;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;

    @Operation(summary = "Delete user by Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User deleted successfully",content = @Content),
            @ApiResponse(responseCode = "403", description = "You do not have the permissions to enter", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
            
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<TypeResolutionContext.Empty> delete(@PathVariable Long id) throws EntityNotFoundException {
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Get all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully get users",content = @Content),
            @ApiResponse(responseCode = "403", description = "You do not have the permissions to enter", content = @Content)})
    @GetMapping()
    public ResponseEntity<List<UserDto>> getAllUsersD() {
        return new ResponseEntity<List<UserDto>>(userService.getAllUsers(), HttpStatus.OK);
    }

    @Operation(summary = "Get user by Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully get user",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))}),
            @ApiResponse(responseCode = "403", description = "You do not have the permissions to enter", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
            
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findUserById(@PathVariable Long id) {
        return ResponseEntity.ok().body(userMapper.convertUserToDto(userService.findUserById(id).get()));
    }

    @Operation(summary = "Update a role")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "the update is correctly",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = MessageInfo.class)
                    ,examples = {@ExampleObject(name = "Example 1", summary = "Update with e new role", description = "when the update is successful send a status 200 message", value = MODEL_ROL_UPDATE)})}),
            @ApiResponse(responseCode = "400", description = "the user already has a role", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = MessageInfo.class)
                    ,examples = {@ExampleObject(name = "Example 1", summary = "User has already the same rol", description = "when try to update a new rol and is the same, sends a 400 (Bad Request) error message", value = MODEL_ROL_ERROR_404)}
               )})
    })
    @PutMapping("/updateRoleUser/{id}")
    public ResponseEntity<MessageInfo> updateRoleUser(@PathVariable Long id, @RequestBody AddRoleToUserForm roleName, WebRequest request) {
        userDetailsCustomService.updateRoleToUser(id, roleName.getRoleName());
        return ResponseEntity.status(HttpStatus.OK).body(messageResponse.messageOk(messageSource.getMessage("user.has.update.role",new Object[]{roleName.getRoleName()}, Locale.ENGLISH), HttpStatus.CREATED.value(), request));
    }
    
    @GetMapping("/accessdenied")
    public ResponseEntity<MessageInfo> accesDenied (WebRequest request){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(messageResponse.messageOk(messageSource.getMessage("user.not.access",null, Locale.ENGLISH), HttpStatus.FORBIDDEN.value(), request));
    }
}
@Data
class AddRoleToUserForm{
    @Schema(name = "roleName", example = "ROLE_ADMIN", type = "String", description = "must be a name of a rol save into the data base")
    private String roleName;
}
