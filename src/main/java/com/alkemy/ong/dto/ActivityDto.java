package com.alkemy.ong.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter @Setter
public class ActivityDto {
    
    private Long id;
    private String name;
    private String content;
    private String image;
    
}
