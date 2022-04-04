package com.alkemy.ong.dto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class SlideUpdateDto {
    private String imageUrl;
    private String text;
    private Integer order;
    private String orgName;
}
