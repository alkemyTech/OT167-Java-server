package com.alkemy.ong.controller;

import com.alkemy.ong.dto.CategoryDto;
import com.alkemy.ong.exception.BadRequestException;
import com.alkemy.ong.exception.DataAlreadyExistException;
import com.alkemy.ong.exception.IncorrectPatternExeption;
import com.alkemy.ong.mapper.CategoryMapper;
import com.alkemy.ong.model.Category;
import com.alkemy.ong.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Controller
@RequestMapping("/categories")
public class CategoryController {


    @Autowired
    private MessageSource messageSource;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CategoryMapper categoryMapper;

    @PostMapping()
    public ResponseEntity<CategoryDto> addNewCategory(@Valid @RequestBody CategoryDto categoryDto) throws DataAlreadyExistException, IncorrectPatternExeption {

        Category category = categoryService.save(categoryMapper.categoryDto2Entity(categoryDto));

        CategoryDto categoryDtoResponse = categoryMapper.categoryEntity2Dto(category);

        return ResponseEntity.status(HttpStatus.CREATED).body(categoryDtoResponse);

    }


    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> categoryDelete(@PathVariable String id){
        categoryService.deleteCategoryById(Long.valueOf(id));
        Map<String, String> message = new HashMap<>(){{put("message: ", messageSource
                .getMessage("category.delete.sucessfuly", new Object[]{id}, Locale.ENGLISH));}};
        return ResponseEntity.ok().body(message);
        }
    }
