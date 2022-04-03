package com.alkemy.ong.dto;

import com.alkemy.ong.model.Organization;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
@RequiredArgsConstructor
public class SlideUpdateDto {
    private String imageUrl;
    private String text;
    @Column(name = "order_number")
    private Integer order;
    private String orgName;
}
