package com.alkemy.ong.utils;

import com.alkemy.ong.dto.NewsDto;
import com.alkemy.ong.model.Category;
import com.alkemy.ong.model.News;

import java.util.UUID;

public class NewsServiceDataUtils {

    public static NewsDto getNewsDto (){
        NewsDto newsDto = new NewsDto();

        newsDto.setId(Long.valueOf(1));
        newsDto.setName("Test");
        newsDto.setContent("Test Content");
        newsDto.setImage("image/Test01");
        newsDto.setCategoryId(Long.valueOf(1));

        return newsDto;
    }

    public static News getNews (){
        News news = new News();

        news.setId(Long.valueOf(1));
        news.setName("Test");
        news.setContent("Test Content");
        news.setImage("image/Test01");

        return news;
    }

    public static Category getCategory(){
        Category category = new Category();

        category.setIdCategories(Long.valueOf(1));
        category.setName("Test Category");
        category.setDescription("Test Description Category");
        category.setImage("src/image02");

        return category;
    }
}
