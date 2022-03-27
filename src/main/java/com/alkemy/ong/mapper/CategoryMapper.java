package com.alkemy.ong.mapper;

import com.alkemy.ong.dto.CategoryDto;
import com.alkemy.ong.model.Category;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CategoryMapper {
    public CategoryDto categoryToDto (Category category){

        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setIdCategories(categoryDto.getIdCategories());
        categoryDto.setName(categoryDto.getName());
        categoryDto.setImage(categoryDto.getImage());
        categoryDto.setDescription(categoryDto.getDescription());

        return categoryDto;
    }

    public Category dtoToCategory(CategoryDto categoryDto){
        Category category = new Category();
        category.setIdCategories(categoryDto.getIdCategories());
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());
        category.setImage(categoryDto.getImage());

        return category;
    }
}
