package com.alkemy.ong.service;

import com.alkemy.ong.dto.NewsDto;

public interface NewsService {

    NewsDto save(NewsDto newsDto);
    NewsDto findById(Long id);

}
