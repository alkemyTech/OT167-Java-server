package com.alkemy.ong.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor

public class CommentBasicDto {
    @Schema(name = "body", example = "1", type = "String", description = "description: comment to show in the page")
    private String body;
}