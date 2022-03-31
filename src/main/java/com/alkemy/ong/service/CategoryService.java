package com.alkemy.ong.service;
import com.alkemy.ong.exception.DataAlreadyExistException;
import com.alkemy.ong.model.Category;
import java.util.Optional;

import java.util.List;

public interface CategoryService {
    Optional<Category> findById(Long id);
    Category save(Category category) throws DataAlreadyExistException;
    public List<String>  getAllCategoriesByName();
    void deleteCategoryById(Long id);
    Category updateCategory(Long id, Category category);
}
