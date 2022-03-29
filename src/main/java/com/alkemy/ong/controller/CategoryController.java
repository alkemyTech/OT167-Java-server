package com.alkemy.ong.controller;

import com.alkemy.ong.dto.CategoryDto;
import com.alkemy.ong.exception.DataAlreadyExistException;
import com.alkemy.ong.exception.IncorrectPatternExeption;
import com.alkemy.ong.mapper.CategoryMapper;
import com.alkemy.ong.model.Category;
import com.alkemy.ong.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryMapper categoryMapper;

    @PostMapping()
    public ResponseEntity<CategoryDto> addNewCategory(@Valid @RequestBody CategoryDto categoryDto) throws DataAlreadyExistException, IncorrectPatternExeption, IncorrectPatternExeption {

        Category category = categoryService.save(categoryMapper.categoryDto2Entity(categoryDto));

        CategoryDto categoryDtoResponse = categoryMapper.categoryEntity2Dto(category);

        return ResponseEntity.status(HttpStatus.CREATED).body(categoryDtoResponse);

    }

    @GetMapping
    public ResponseEntity<List<String>> listCategoriesByName(){
        return ResponseEntity.ok().body(categoryService.getAllCategoriesByName());
    }

}
