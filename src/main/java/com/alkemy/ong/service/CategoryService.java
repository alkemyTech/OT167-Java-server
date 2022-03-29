package com.alkemy.ong.service;


import com.alkemy.ong.exception.DataAlreadyExistException;
import com.alkemy.ong.model.Category;

public interface CategoryService {

    Category save(Category category) throws DataAlreadyExistException;

}
