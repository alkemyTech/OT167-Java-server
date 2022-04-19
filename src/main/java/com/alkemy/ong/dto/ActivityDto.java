package com.alkemy.ong.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

@RequiredArgsConstructor
@Getter @Setter
public class ActivityDto {
    private Long id;

    @Nullable
    private String name;
    @Nullable
    private String content;
    private String image;
    
}
