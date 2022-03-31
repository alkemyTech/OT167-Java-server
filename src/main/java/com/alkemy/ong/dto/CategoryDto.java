package com.alkemy.ong.dto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@RequiredArgsConstructor
public class CategoryDto {
    @NotBlank(message = "Name is mandatory")
    private String name;
    private Long id;
    private String description;
    private String image;
}
