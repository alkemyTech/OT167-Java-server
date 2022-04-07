package com.alkemy.ong.controller;
import com.alkemy.ong.mapper.CategoryMapper;
import com.alkemy.ong.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.alkemy.ong.dto.CategoryDto;
import com.alkemy.ong.exception.DataAlreadyExistException;
import com.alkemy.ong.exception.IncorrectPatternExeption;
import com.alkemy.ong.model.Category;
import com.alkemy.ong.exception.NotFoundException;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CategoryMapper categoryMapper;

    @GetMapping
    public ResponseEntity<List<String>> listCategoriesByName(){
        return ResponseEntity.ok().body(categoryService.getAllCategoriesByName());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("{id}")
    public ResponseEntity<?> getCategory(@Valid @PathVariable Long id) {
        return ResponseEntity.ok(categoryMapper.categoryEntity2Dto(categoryService.findById(id).get()));
    }

    @PostMapping
    public ResponseEntity<CategoryDto> addNewCategory(@Valid @RequestBody CategoryDto categoryDto) throws DataAlreadyExistException, IncorrectPatternExeption {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.save(categoryDto));

    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Long id,@RequestBody Category category){
        try{
            Category categoryUpdated= categoryService.updateCategory(id,category);
            CategoryDto categoryDtoResponse = categoryMapper.categoryEntity2Dto(categoryUpdated);
            return ResponseEntity.ok(categoryDtoResponse);
        }catch (NotFoundException e){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(category);
        }
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> categoryDelete(@PathVariable String id){
        categoryService.deleteCategoryById(Long.valueOf(id));
        Map<String, String> message = new HashMap<>(){{put("message: ", messageSource
                .getMessage("category.delete.sucessfuly", new Object[]{id}, Locale.ENGLISH));}};
        return ResponseEntity.ok().body(message);
        }
    }
