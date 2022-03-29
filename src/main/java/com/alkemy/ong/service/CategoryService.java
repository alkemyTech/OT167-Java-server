package com.alkemy.ong.service;

import com.alkemy.ong.model.Category;
import com.alkemy.ong.exception.DataAlreadyExistException;
import java.util.Optional;

public interface CategoryService {

    Optional<Category> findById(Long id);
    Category save(Category category) throws DataAlreadyExistException;

}
