package com.alkemy.ong.controller;

import com.alkemy.ong.mapper.CategoryMapper;
import com.alkemy.ong.mapper.OrganizationMapper;
import com.alkemy.ong.service.CategoryService;
import com.alkemy.ong.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryMapper categoryMapper;

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategory(@Valid @PathVariable Long id){
        return ResponseEntity.ok(categoryMapper.categoryToDto(categoryService.findById(id).get()));
    }
}
