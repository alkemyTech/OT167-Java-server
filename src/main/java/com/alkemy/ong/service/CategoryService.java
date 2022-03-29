package com.alkemy.ong.service;

import com.alkemy.ong.exception.DataAlreadyExistException;
import com.alkemy.ong.model.Category;

import java.util.List;

public interface CategoryService {

    Category save(Category category) throws DataAlreadyExistException;

    public List<String>  getAllCategoriesByName();
}
