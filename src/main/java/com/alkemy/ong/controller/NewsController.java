package com.alkemy.ong.controller;

import com.alkemy.ong.dto.NewsDto;
import com.alkemy.ong.exception.PaginationMessage;
import com.alkemy.ong.model.News;
import com.alkemy.ong.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/news")
@Validated
public class NewsController {

    @Autowired
    private NewsService newsService;

    @Autowired
    private MessageSource messageSource;
    @Autowired
    private PaginationMessage paginationMessage;

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

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable Long id) {
        this.newsService.delete(id);
        Map<String, String> message = new HashMap<>() {{
            put("message: ", messageSource
                    .getMessage("news.delete.ok", new Object[]{id}, Locale.ENGLISH));
        }};
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }
    @GetMapping("/query")
    public ResponseEntity<?> findAllNewsPag(@RequestParam(value = "page", required = true) String page, WebRequest request){
        return ResponseEntity.status(HttpStatus.OK).body(newsService.findAllPag(Integer.parseInt(page), request));
    }
}
