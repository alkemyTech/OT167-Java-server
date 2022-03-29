package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.CategoryDto;
import com.alkemy.ong.exception.DataAlreadyExistException;
import com.alkemy.ong.mapper.CategoryMapper;
import com.alkemy.ong.model.Category;
import com.alkemy.ong.repository.CategoryRepository;
import com.alkemy.ong.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private MessageSource messageSource;

    public Category save(Category category) throws DataAlreadyExistException {
        Category categorySaved = null;

        try{
            if(categoryRepository.findByName(category.getName()) == null){
                categorySaved = categoryRepository.save(category);
            }
        }catch (Exception ex) {
            throw new DataAlreadyExistException(messageSource.getMessage("category.already.exist", null, Locale.ENGLISH));
        }
        return categorySaved;
    }

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
        List<String> ListcategoriesByName = new ArrayList<>();
        for (CategoryDto categoryDto : listAllCategoryDto){
            ListcategoriesByName.add(categoryDto.getName());
    }
    return ListcategoriesByName;

    }
}
