package com.alkemy.ong.dto;

import com.alkemy.ong.model.Role;
import javax.validation.constraints.*;

import com.alkemy.ong.model.User;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterRequest {

    @NotBlank(message = "Name is mandatory.")
    private String firstName;

    @NotBlank(message = "Last name is mandatory.")
    private String lastName;

    @NotBlank(message = "Email is mandatory.")
    @Email(message = "Email should be valid.", regexp = "[A-Za-z0-9._%-+]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}")
    private String email;

    @NotBlank(message = "Password is mandatory.")
    @Size(min = 6, message = "The password must be at least 6 characters")
    
    private String password;
    private String photo;
    private LocalDate creationDate;
    private Role rol;
    private List<RoleDto> roles;
    

    
}