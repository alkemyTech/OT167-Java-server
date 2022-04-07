package com.alkemy.ong.service;

import com.alkemy.ong.dto.NewsDto;
import com.alkemy.ong.exception.MessagePag;
import com.alkemy.ong.model.News;
import org.springframework.web.context.request.WebRequest;

import java.util.Optional;

public interface NewsService {

    NewsDto save(NewsDto newsDto);

    NewsDto findById(Long id);

    Optional<News> findNewById(Long id);

    void delete(Long id);

    MessagePag findAllPag(int page, WebRequest request);
}
