package com.alkemy.ong.mapper;


import com.alkemy.ong.dto.CategoryDto;
import com.alkemy.ong.exception.IncorrectPatternExeption;
import com.alkemy.ong.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class CategoryMapper {

    @Autowired
    private MessageSource messageSource;

    public Category categoryDto2Entity(CategoryDto categoryDto) throws IncorrectPatternExeption {

        Category newCategory = new Category();

        newCategory.setName(validate(categoryDto.getName()));
        newCategory.setDescription(categoryDto.getDescription());
        newCategory.setImage(categoryDto.getImage());
        return newCategory;

    }

    private String validate(String parameter) throws IncorrectPatternExeption {
        boolean valid = parameter.matches("[A-Za-z]{1,4}");
        if(!valid) {
            throw new IncorrectPatternExeption(messageSource.getMessage("data.incorrect", null, Locale.ENGLISH));
        }
        return parameter;
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
