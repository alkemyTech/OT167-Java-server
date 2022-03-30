package com.alkemy.ong.service.impl;


import com.alkemy.ong.dto.CategoryDto;
import com.alkemy.ong.exception.DataAlreadyExistException;
import com.alkemy.ong.exception.NotFoundException;
import com.alkemy.ong.model.Category;
import com.alkemy.ong.repository.CategoryRepository;
import com.alkemy.ong.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryServiceImpl implements CategoryService {


    private final CategoryRepository categoryRepository;

    private final MessageSource messageSource;
    @Override
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

    @Override
    public void deleteCategoryById(Long id) {
        Optional<Category> category = Optional.ofNullable(categoryRepository.findById(id).orElseThrow(() -> new NotFoundException(messageSource
                .getMessage("category.not.found", new Object[]{id.toString()}, Locale.ENGLISH))));
        categoryRepository.delete(category.get());
    }
}
