package com.alkemy.ong.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class SlideBasicDto {

    private String imageUrl;

    private Integer order;

}