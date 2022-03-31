package com.alkemy.ong.dto;

import lombok.*;

import javax.validation.constraints.*;

@RequiredArgsConstructor
@Getter @Setter
public class CategoryDto {
    @NotBlank(message = "Name is mandatory")
    //@Pattern("{\D}")
    private String name;
    private Long id;
    private String description;
    private String image;

}
