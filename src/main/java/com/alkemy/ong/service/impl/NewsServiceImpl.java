package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.NewsDto;
import com.alkemy.ong.mapper.NewsMapper;
import com.alkemy.ong.model.News;
import com.alkemy.ong.repository.NewsRepository;
import com.alkemy.ong.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {

    @Autowired
    private NewsMapper newsMapper;

    @Autowired
    private NewsRepository newsRepository;

    @Override
    public NewsDto save(NewsDto newsDto) {

        News newsEnt = newsMapper.newsDTO2Entity(newsDto);
        News newsSaved = newsRepository.save(newsEnt);
        NewsDto result = newsMapper.newsEntity2Dto(newsSaved);

        return result;
    }
}
