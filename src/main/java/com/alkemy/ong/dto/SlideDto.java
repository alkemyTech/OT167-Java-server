package com.alkemy.ong.dto;

import com.alkemy.ong.model.Organization;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import javax.persistence.Column;
@Getter
@Setter
@RequiredArgsConstructor
public class SlideDto {
    private Long id;
    private String imageUrl;
    private String text;
    @Column(name = "order_number")
    private Integer order;
    private Organization organization;
}
