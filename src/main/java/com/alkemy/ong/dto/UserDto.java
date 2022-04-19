package com.alkemy.ong.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @Schema(name = "id", type = "Long", example = "1",hidden = true)
    private Long id;
    @Schema(name = "firstName", example = "Mickaela", type = "String",description = "description : name of the user (not allows null)")
    @NotNull(message = "The first name cannot be empty")
    private String firstName;
    @Schema(name = "lastName", example = "Tarazaga", type = "String",description = "description: last name of the user (not allows null)")
    @NotNull(message = "The last name cannot be empty")
    private String lastName;
    @Schema(name = "email", example = "tarazaga.mickaela@gmail.com", type = "String",description = "description: email of the user (not allows null)")
    @NotNull(message = "The email cannot be empty")
    private String email;
    @Schema(name = "photo", example = "url.com/image.jpg", type = "String",description = "description: image of the user (not allows null)")
    @NotNull(message = "The photo cannot be empty")
    private String photo;

}
