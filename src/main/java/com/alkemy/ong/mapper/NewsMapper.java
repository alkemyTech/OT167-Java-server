package com.alkemy.ong.mapper;

import com.alkemy.ong.dto.NewsDto;
import com.alkemy.ong.model.Category;
import com.alkemy.ong.model.News;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

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
        news.setCategoryId(categoryEntity);
        return news;

    }

    //=== Entity --> DTO
    public NewsDto newsEntity2Dto(News news){

        NewsDto newsDto = new NewsDto();
        newsDto.setId(news.getId());
        newsDto.setName(news.getName());
        newsDto.setImage(news.getImage());
        newsDto.setContent(news.getContent());
        newsDto.setCategoryId(news.getCategoryId().getIdCategories());
        return newsDto;
    }

}
