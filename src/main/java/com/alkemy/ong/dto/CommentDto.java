package com.alkemy.ong.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class CommentDto {
    @Schema(name = "id", example = "1", type = "integer", nullable = false, description = "id: autogenerated", hidden = true)
    private Long id;
    private String body;
    private Long user_id;
    private Long news_id;
}
