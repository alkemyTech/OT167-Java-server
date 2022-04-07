package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.NewsDto;
import com.alkemy.ong.exception.MessagePag;
import com.alkemy.ong.exception.NotFoundException;
import com.alkemy.ong.exception.PaginationMessage;
import com.alkemy.ong.mapper.NewsMapper;
import com.alkemy.ong.model.News;
import com.alkemy.ong.repository.NewsRepository;
import com.alkemy.ong.service.NewsService;

import java.util.Locale;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;

@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {

    private static final int SIZE_PAG_10 = 9;
    @Autowired
    private PaginationMessage paginationMessage;
    private WebRequest request;

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

    @Override
    public NewsDto findById(Long id) {
        Optional<News> news = newsRepository.findById(id);
        if (!news.isPresent()) {
            throw new NotFoundException(messageSource.getMessage("not.found.news", null, Locale.ENGLISH));
        }
        return newsMapper.newsEntity2Dto(news.get());
    }

    @Override
    public Optional<News> findNewById(Long news_id) {
        return Optional.ofNullable(newsRepository.findById(news_id).orElseThrow(() -> new NotFoundException(messageSource.getMessage("news.not.null", null, Locale.ENGLISH))));
    }

    @Override
    public void delete(Long id) {
        Optional<News> news = this.newsRepository.findById(id);
        if (!news.isPresent()) {
            throw new NotFoundException(messageSource.getMessage
                    ("news.not.found", null, Locale.ENGLISH));
        }
        newsRepository.deleteById(id);
    }
    @Override
    public MessagePag findAllPag(int page, WebRequest request) {
        Page newsPage = newsRepository.findAll(PageRequest.of(page, SIZE_PAG_10));
        return paginationMessage.messageInfo(newsPage, newsMapper.listNewsDto(newsPage.getContent()), request);
    }
}
