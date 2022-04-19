package com.alkemy.ong.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@RequiredArgsConstructor
@Getter @Setter
public class UserDtoCreator {
    @Schema(name = "firstName", example = "Mickaela", type = "String",description = "description : name of the user (not allows null)")
    @NotNull(message = "The first name cannot be empty")
    private String firstName;
    @Schema(name = "lastName", example = "Tarazaga", type = "String",description = "description: last name of the user (not allows null)")
    @NotNull(message = "The last name cannot be empty")
    private String lastName;
    @Schema(name = "email", example = "tarazaga.mickaela@gmail.com", type = "String",description = "description: email of the user (not allows null)")
    @NotNull(message = "The email cannot be empty")
    private String email;
    @Schema(name = "password", example = "12345678", type = "String",description = "description: password of the user (not allows null)")
    @NotNull(message = "The password cannot be empty")
    private String password;
    @Schema(name = "photo", example = "url.com/image.jpg", type = "String",description = "description: image of the user (not allows null)")
    @NotNull(message = "The photo cannot be empty")
    private String photo;
}
