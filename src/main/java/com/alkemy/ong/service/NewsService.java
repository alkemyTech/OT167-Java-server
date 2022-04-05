package com.alkemy.ong.service;

import com.alkemy.ong.dto.NewsDto;
import com.alkemy.ong.model.News;
import com.alkemy.ong.security.model.UserEntity;

import java.util.Optional;

public interface NewsService {

    NewsDto save(NewsDto newsDto);
    NewsDto findById(Long id);

    Optional<News> findById(Long id);
}
