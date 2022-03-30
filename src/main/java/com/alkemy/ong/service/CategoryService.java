package com.alkemy.ong.service;


import java.util.List;

public interface CategoryService {

    public List<String>  getAllCategoriesByName();

import com.alkemy.ong.exception.DataAlreadyExistException;
import com.alkemy.ong.model.Category;

public interface CategoryService {

    Category save(Category category) throws DataAlreadyExistException;

    Category updateCategory(Long id, Category category);

}
