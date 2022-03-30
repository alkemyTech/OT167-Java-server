package com.alkemy.ong.dto;


import lombok.*;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {


    @NotBlank(message = "Name is mandatory")
    private String name;
    private Long id;
    private String description;
    private String image;
}
