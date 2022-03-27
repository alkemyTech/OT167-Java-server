package com.alkemy.ong.service.impl;


import com.alkemy.ong.dto.CategoryDto;
import com.alkemy.ong.exception.DataAlreadyExistException;
import com.alkemy.ong.model.Category;
import com.alkemy.ong.repository.CategoryRepository;
import com.alkemy.ong.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
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
}
