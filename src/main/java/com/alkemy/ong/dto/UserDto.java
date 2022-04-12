package com.alkemy.ong.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @Schema(name = "id", type = "Long", example = "1")
    private Long id;
    @Schema(name = "firstName", example = "Mickaela", type = "String")
    private String firstName;
    @Schema(name = "lastName", example = "Tarazaga", type = "String")
    private String lastName;
    @Schema(name = "email", example = "tarazaga.mickaela@gmail.com", type = "String")
    private String email;
    @Schema(name = "photo", example = "url.com/image.jpg", type = "String")
    private String photo;

}
