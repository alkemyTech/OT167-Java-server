package com.alkemy.ong.service;

import com.alkemy.ong.dto.CategoryDto;
import com.alkemy.ong.exception.DataAlreadyExistException;
import com.alkemy.ong.exception.IncorrectPatternExeption;
import com.alkemy.ong.exception.MessagePag;
import com.alkemy.ong.model.Category;
import java.util.List;
import java.util.Optional;
import org.springframework.web.context.request.WebRequest;

public interface CategoryService {
    Optional<Category> findById(Long id);
    CategoryDto save(CategoryDto category) throws DataAlreadyExistException, IncorrectPatternExeption;
    void deleteCategoryById(Long id);
    Category updateCategory(Long id, Category category);
    List<String> getAllCategoriesByName();
    MessagePag findAllPag(int page, WebRequest request);
}
