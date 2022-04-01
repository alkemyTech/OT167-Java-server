package com.alkemy.ong.controller;

import com.alkemy.ong.dto.NewsDto;
import com.alkemy.ong.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/news")
@Validated
public class NewsController {

    @Autowired
    private NewsService newsService;

    @PostMapping
    public ResponseEntity<NewsDto> save(@Valid @RequestBody NewsDto newsDto){
        NewsDto newsSaved = newsService.save(newsDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newsSaved);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<NewsDto> findNewsById(@PathVariable Long id){
        NewsDto newsDto = newsService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(newsDto);
    }
}
