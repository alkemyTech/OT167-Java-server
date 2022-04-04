package com.alkemy.ong.dto;

import com.alkemy.ong.model.News;
import com.alkemy.ong.security.model.UserEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class CommentDto {

    private Long id;
    private String body;
    private Long user_id;
    private Long news_id;
}
