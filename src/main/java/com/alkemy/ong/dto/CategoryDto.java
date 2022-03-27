package com.alkemy.ong.dto;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.validation.constraints.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@Getter @Setter
public class CategoryDto {
    
    @NotBlank(message = "Name is mandatory")
    private String name;

    private String description;
    private String image;

}
