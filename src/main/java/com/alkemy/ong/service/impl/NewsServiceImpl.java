package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.NewsDto;
import com.alkemy.ong.exception.NotFoundException;
import com.alkemy.ong.mapper.NewsMapper;
import com.alkemy.ong.model.News;
import com.alkemy.ong.repository.NewsRepository;
import com.alkemy.ong.service.NewsService;
import java.util.Locale;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {

    @Autowired
    private NewsMapper newsMapper;

    @Autowired
    private NewsRepository newsRepository;
    
    @Autowired
    private MessageSource messageSource;


    @Override
    public NewsDto save(NewsDto newsDto) {

        News newsEnt = newsMapper.newsDTO2Entity(newsDto);
        News newsSaved = newsRepository.save(newsEnt);
        NewsDto result = newsMapper.newsEntity2Dto(newsSaved);

        return result;
    }


    public void delete(Long id) {
        Optional<News> news = this.newsRepository.findById(id);
        if (!news.isPresent()) {
            throw new NotFoundException(messageSource.getMessage
                    ("news.not.found", null, Locale.ENGLISH));
        }
        newsRepository.deleteById(id);
    }
    
    @Override
    public NewsDto findById(Long id){
        Optional<News> news = newsRepository.findById(id);
        if (!news.isPresent()) {
            throw new NotFoundException(messageSource.getMessage("not.found.news", null, Locale.ENGLISH));          
        }
        return newsMapper.newsEntity2Dto(news.get());

    }
}
