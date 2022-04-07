package com.alkemy.ong.service;

import com.alkemy.ong.dto.CategoryDto;
import com.alkemy.ong.exception.DataAlreadyExistException;
import com.alkemy.ong.exception.IncorrectPatternExeption;
import com.alkemy.ong.model.Category;
import java.util.List;
import java.util.Optional;

public interface CategoryService {
    Optional<Category> findById(Long id);
    CategoryDto save(CategoryDto categoryDto) throws DataAlreadyExistException, IncorrectPatternExeption;
    void deleteCategoryById(Long id);
    Category updateCategory(Long id, Category category);
    List<String> getAllCategoriesByName();
}
