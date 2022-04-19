package com.alkemy.ong.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@RequiredArgsConstructor
public class SlideBasicDto {
    @Schema(name = "imageUrl", example = "url.com/image.jpg", type = "String", description = "description : slide image url")
    @NotNull(message = "The image cannot be empty")
    private String imageUrl;
    @Schema(name = "order", example = "1", type = "Integer",nullable = true, description = "description : slider order")
    private Integer order;
}