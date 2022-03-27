package com.alkemy.ong.service.impl;


import com.alkemy.ong.exception.NotFoundException;
import com.alkemy.ong.model.Category;
import com.alkemy.ong.repository.CategoryRepository;
import com.alkemy.ong.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final MessageSource messageSource;


    @Override
    public Optional<Category> findById(Long id) {
        if(categoryRepository.findById(id).isEmpty()) {
            throw new NotFoundException(messageSource.getMessage("not.found",null, Locale.ENGLISH));
        }
        return categoryRepository.findById(id);
    }
}
