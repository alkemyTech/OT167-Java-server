package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.CategoryDto;
import com.alkemy.ong.mapper.CategoryMapper;
import com.alkemy.ong.model.Category;
import com.alkemy.ong.repository.CategoryRepository;
import com.alkemy.ong.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private MessageSource messageSource;

    private List<Category> getALLCategories(){
        return categoryRepository.findAll();
    }

    private List<CategoryDto> listAllCategoryDto() {
        List<Category> categories = getALLCategories();
        List<CategoryDto> categoryDtos = new ArrayList<>();
        for (Category category : categories) {
            categoryDtos.add(categoryMapper.categoryEntity2Dto(category));
        }
        return categoryDtos;
    }

    public List<String> getAllCategoriesByName() {
        List<CategoryDto> listAllCategoryDto = listAllCategoryDto();
        return listAllCategoryDto.stream()
                .map(categoryDto ->  categoryDto.getName())
                .collect(Collectors.toList());
    }
}

