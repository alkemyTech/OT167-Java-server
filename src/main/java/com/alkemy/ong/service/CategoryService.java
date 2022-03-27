package com.alkemy.ong.service;

import com.alkemy.ong.model.Category;

import java.util.Optional;

public interface CategoryService {

    Optional<Category> findById(Long id);
}
