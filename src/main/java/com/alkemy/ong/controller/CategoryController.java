package com.alkemy.ong.controller;

import com.alkemy.ong.dto.CategoryDto;
import com.alkemy.ong.exception.DataAlreadyExistException;
import com.alkemy.ong.exception.NotFoundException;
import com.alkemy.ong.mapper.CategoryMapper;
import com.alkemy.ong.model.Category;
import com.alkemy.ong.service.CategoryService;
import com.sendgrid.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryMapper categoryMapper;

    @PostMapping()
    public ResponseEntity<CategoryDto> addNewCategory(@Valid @RequestBody CategoryDto categoryDto) throws DataAlreadyExistException {

        Category category = categoryService.save(categoryMapper.categoryDto2Entity(categoryDto));

        CategoryDto categoryDtoResponse = categoryMapper.categoryEntity2Dto(category);

        return ResponseEntity.status(HttpStatus.CREATED).body(categoryDtoResponse);

    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Long id,@RequestBody Category category){
        try{
            Category category1= categoryService.updateCategory(id,category);

            CategoryDto categoryDtoResponse = categoryMapper.categoryEntity2Dto(category);

            return ResponseEntity.ok(categoryDtoResponse);
        }catch (NotFoundException e){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(category);
        }
    }
}