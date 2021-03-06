package com.alkemy.ong.security.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.*;
import java.time.LocalDate;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterRequest {

    @Schema(name = "firstName", example = "Mariana", type = "String")
    @NotBlank(message = "Name is mandatory.")
    private String firstName;

    @Schema(name = "lastName", example = "López", type = "String")
    @NotBlank(message = "Last name is mandatory.")
    private String lastName;
    
    @Schema(name = "email", example = "marianalopez@mail.com", type = "String")
    @NotBlank(message = "Email is mandatory.")
    @Email(message = "Email should be valid.", regexp = "[A-Za-z0-9._%-+]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}")
    private String email;

    @Schema(name = "password", example = "password123", type = "String")
    @NotBlank(message = "Password is mandatory.")
    @Size(min = 6, message = "The password must be at least 6 characters")
    private String password;
    
    @Schema(name = "photo", example = "url.com/image.jpg", type = "String")
    private String photo;
  
}