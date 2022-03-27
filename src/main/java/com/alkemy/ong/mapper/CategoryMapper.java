package com.alkemy.ong.mapper;


import com.alkemy.ong.dto.CategoryDto;
import com.alkemy.ong.model.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {


    public Category categoryDto2Entity(CategoryDto categoryDto) {

        Category newCategory = new Category();

        newCategory.setName(categoryDto.getName());
        newCategory.setDescription(categoryDto.getDescription());
        newCategory.setImage(categoryDto.getImage());
        return newCategory;

    }

    public CategoryDto categoryEntity2Dto(Category category){

        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getIdCategories());
        categoryDto.setName(category.getName());
        categoryDto.setDescription(category.getDescription());
        categoryDto.setImage(category.getImage());
        return categoryDto;
    }
}
