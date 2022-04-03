package com.alkemy.ong.dto;
import com.alkemy.ong.model.Organization;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@RequiredArgsConstructor
public class SlideDto {
    private Long id;
    private String imageUrl;
    private String text;
    private Integer order;
    private Organization organization;
}
