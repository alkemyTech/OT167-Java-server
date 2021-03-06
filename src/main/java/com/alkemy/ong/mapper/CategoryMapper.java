package com.alkemy.ong.mapper;

import com.alkemy.ong.dto.CategoryDto;
import com.alkemy.ong.exception.IncorrectPatternExeption;
import com.alkemy.ong.model.Category;
import com.alkemy.ong.service.impl.CategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;


import java.util.ArrayList;
import java.util.List;


@Component
public class CategoryMapper {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private CategoryServiceImpl categoryService;

    public Category categoryDto2Entity(CategoryDto categoryDto) throws IncorrectPatternExeption {

        Category newCategory = new Category();

        newCategory.setName(categoryDto.getName());

        newCategory.setName(categoryService.validate(categoryDto.getName()));

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

    public List<Category> categoryDTOList2EntityList(List<CategoryDto> dtos) throws IncorrectPatternExeption {
        List<Category> categories = new ArrayList<>();
        for (CategoryDto dto : dtos){
            categories.add(this.categoryDto2Entity(dto));
        }
        return categories;
    }

    public List<CategoryDto> categoryList2CategoryDTOList(List<Category> categories){
        List<CategoryDto> dtos = new ArrayList<>();
        for (Category category : categories){
            dtos.add(categoryEntity2Dto(category));
        }
        return dtos;
    }

}
