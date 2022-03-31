package com.alkemy.ong.service.impl;

import com.alkemy.ong.exception.NotFoundException;
import com.alkemy.ong.exception.DataAlreadyExistException;
import com.alkemy.ong.model.Category;
import com.alkemy.ong.repository.CategoryRepository;
import com.alkemy.ong.service.CategoryService;
import lombok.RequiredArgsConstructor;
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
    public Optional<Category> findById(Long id) {
        if (categoryRepository.findById(id).isEmpty()) {
            throw new NotFoundException(messageSource.getMessage("not.found", null, Locale.ENGLISH));
        }
        return categoryRepository.findById(id);
    }

    public Category updateCategory(Long id, Category category ){
        Category categoryExist=categoryRepository.findById(id).get();

        if (categoryExist != null){
            category.setIdCategories(categoryExist.getIdCategories());

            return categoryRepository.save(category);
        }else{
            throw new NotFoundException(messageSource.getMessage("category.not.found", null,Locale.ENGLISH));
        }
    }
}