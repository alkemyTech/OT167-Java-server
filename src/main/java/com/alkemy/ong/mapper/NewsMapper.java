package com.alkemy.ong.mapper;

import com.alkemy.ong.dto.NewsDto;
import com.alkemy.ong.model.Category;
import com.alkemy.ong.model.News;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class NewsMapper {

    //=== DTO --> Entity
    public News newsDTO2Entity(NewsDto dto){

        Category categoryEntity = new Category();
        categoryEntity.setIdCategories(dto.getCategoryId());
        News news = new News();
        news.setImage(dto.getImage());
        news.setContent(dto.getContent());
        news.setName(dto.getName());
        if(news.getCategoryId() !=null) news.setCategoryId(categoryEntity);
        return news;

    }
    public List<NewsDto> listNewsDto(List<News> newsList){
        return newsList.stream().map(news -> newsEntity2Dto(news)).collect(Collectors.toList());
    }

    //=== Entity --> DTO
    public NewsDto newsEntity2Dto(News news){

        NewsDto newsDto = new NewsDto();
        newsDto.setId(news.getId());
        newsDto.setName(news.getName());
        newsDto.setImage(news.getImage());
        newsDto.setContent(news.getContent());
        if(news.getCategoryId() !=null) newsDto.setCategoryId(news.getCategoryId().getIdCategories());
        return newsDto;
    }

}
