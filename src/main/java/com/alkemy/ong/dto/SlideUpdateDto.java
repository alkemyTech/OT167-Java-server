package com.alkemy.ong.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@RequiredArgsConstructor
public class SlideUpdateDto {
    @Schema(name = "imageUrl", example = "/img.jpg", type = "String", description = "description : slide image url (not allows null)")
    @NotNull(message = "The image cannot be empty")
    private String imageUrl;
    @Schema(name = "text", example = "Welcome to the web side!",nullable = true, type = "String", description = "description : slide text")
    private String text;
    @Schema(name = "order", example = "1", type = "Integer",nullable = true, description = "description : slider order")
    private Integer order;
    @Schema(name = "orgName", example = "Somos Más", type = "String",nullable = true, description = "description : organization name")
    private String orgName;
}