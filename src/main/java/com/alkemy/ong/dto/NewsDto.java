package com.alkemy.ong.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@RequiredArgsConstructor
public class NewsDto {

    private Long id;

    private String name;

    private String content;

    private String image;

    private Long categoryId;

}
