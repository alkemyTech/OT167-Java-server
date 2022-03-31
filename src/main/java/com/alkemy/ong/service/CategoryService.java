package com.alkemy.ong.service;

import com.alkemy.ong.exception.DataAlreadyExistException;
import com.alkemy.ong.model.Category;
import java.util.List;
import java.util.Optional;

public interface CategoryService {
    Optional<Category> findById(Long id);
    Category save(Category category) throws DataAlreadyExistException;
    void deleteCategoryById(Long id);
    Category updateCategory(Long id, Category category);
    List<String> getAllCategoriesByName();
}
