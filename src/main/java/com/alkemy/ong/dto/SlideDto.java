package com.alkemy.ong.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class SlideDto {
    
    private Long id;
    private String imageUrl;
    private String text;
    private Integer order;
    private Long organization_id;
}
