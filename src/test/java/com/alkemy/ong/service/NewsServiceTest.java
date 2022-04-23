package com.alkemy.ong.service;

import com.alkemy.ong.dto.NewsDto;
import com.alkemy.ong.exception.MessagePag;
import com.alkemy.ong.exception.NotFoundException;
import com.alkemy.ong.exception.PaginationMessage;
import com.alkemy.ong.mapper.NewsMapper;
import com.alkemy.ong.model.Category;
import com.alkemy.ong.model.News;
import com.alkemy.ong.repository.CategoryRepository;
import com.alkemy.ong.repository.NewsRepository;
import com.alkemy.ong.service.impl.NewsServiceImpl;
import com.alkemy.ong.utils.NewsServiceDataUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import java.util.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class NewsServiceTest {

    @InjectMocks
    private NewsServiceImpl newsService;

    @Mock
    private NewsRepository newsRepository;

    @Mock
    private NewsMapper newsMapper;

    @Mock
    private MessageSource messageSource;

    @Mock
    private PaginationMessage paginationMessage;

    @Mock
    private CategoryRepository categoryRepository;

    @Test
    void save() {

        NewsDto newsDto = NewsServiceDataUtils.getNewsDto();
        News news = NewsServiceDataUtils.getNews();

        given(newsMapper.newsDTO2Entity(newsDto)).willReturn(news);
        given((newsRepository.save(news))).willAnswer(invocationOnMock -> invocationOnMock.getArgument(0));
        given(newsMapper.newsEntity2Dto(news)).willReturn(newsDto);

        NewsDto saveNews = newsService.save(newsDto);

        Assertions.assertNotNull(saveNews);

        verify(newsRepository).save(any(News.class));
    }

    @Test
    void findByIdWhenIsNotPresent(){
        NewsDto newsDto = NewsServiceDataUtils.getNewsDto();

        given(newsRepository.findById(newsDto.getId())).willReturn(Optional.empty());
        NotFoundException notFoundException = Assertions.assertThrows(NotFoundException.class,
                ()-> newsService.findById(newsDto.getId()));

        Assertions.assertEquals(messageSource.getMessage("not.found.news", null, Locale.ENGLISH),
                notFoundException.getMessage());

        verify(newsRepository).findById(anyLong());
    }

    @Test
    void findByIdWhenIsPresent(){
        NewsDto newsDto = NewsServiceDataUtils.getNewsDto();
        Optional<News> findById = Optional.of(NewsServiceDataUtils.getNews());

        given(newsRepository.findById(newsDto.getId())).willReturn(findById);
        given(newsMapper.newsEntity2Dto(findById.get())).willReturn(newsDto);

        NewsDto findNews = newsService.findById(newsDto.getId());

        Assertions.assertNotNull(findNews);

        verify(newsRepository).findById(anyLong());
        verify(newsMapper).newsEntity2Dto(any(News.class));
    }

    @Test
    void findByIdWhenIsNotPresentWhenDelete(){
        NewsDto newsDto = NewsServiceDataUtils.getNewsDto();

        given(newsRepository.findById(newsDto.getId())).willReturn(Optional.empty());
        NotFoundException notFoundException = Assertions.assertThrows(NotFoundException.class,
                ()-> newsService.delete(newsDto.getId()));

        Assertions.assertEquals(messageSource.getMessage("not.found.news", null, Locale.ENGLISH),
                notFoundException.getMessage());

        verify(newsRepository).findById(anyLong());
    }

    @Test
    void findByIdWhenIsPresentWhenDelete(){
        NewsDto newsDto = NewsServiceDataUtils.getNewsDto();
        Optional<News> findById = Optional.of(NewsServiceDataUtils.getNews());

        given(newsRepository.findById(newsDto.getId())).willReturn(findById);

        newsService.delete(newsDto.getId());

        verify(newsRepository).findById(anyLong());
        verify(newsRepository).deleteById(anyLong());
    }
    /*
        @Test
        void findAllPag(){
            MockHttpServletRequest servletRequest = new MockHttpServletRequest();
            servletRequest.setRequestURI("/query");
            WebRequest request = new ServletWebRequest(servletRequest);
            List<News> news = new ArrayList<>();
            List<NewsDto> newsDtos = new ArrayList<>();
            Pageable pageable = PageRequest.of(1, 10);
            Page<News> newsPage = new PageImpl<>(news, pageable, 1);

            given(newsRepository.findAll(PageRequest.of(1, 10))).willReturn(newsPage);
            given(newsMapper.listNewsDto(newsPage.getContent())).willReturn(newsDtos);
            MessagePag messagePag = paginationMessage.messageInfo(newsPage, Collections.singletonList(newsDtos), request);
            given(paginationMessage.messageInfo(any(), any(), any())).willReturn(messagePag);

            MessagePag messagePag1 = newsService.findAllPag(1, request);

            Assertions.assertNotNull(messagePag1);

            verify(newsMapper).listNewsDto(any());
            verify(newsRepository).findAll(any(Pageable.class));
        }
    */
    @Test
    void findByIdCategoryWhenIsNotPresent(){
        News news = NewsServiceDataUtils.getNews();
        NewsDto newsDto = NewsServiceDataUtils.getNewsDto();

        NotFoundException notFoundException = Assertions.assertThrows(NotFoundException.class,
                ()-> newsService.updateNews(news.getId(), newsDto));

        Assertions.assertEquals(messageSource.getMessage("not.found", null, Locale.ENGLISH),
                notFoundException.getMessage());
    }

    @Test
    void findByIdCategoryWhenIsPresent(){
        Category category = NewsServiceDataUtils.getCategory();
        Optional<Category> findById = Optional.of(NewsServiceDataUtils.getCategory());
        News news = NewsServiceDataUtils.getNews();
        Optional<News> findByIdNews = Optional.of(NewsServiceDataUtils.getNews());
        NewsDto newsDto = NewsServiceDataUtils.getNewsDto();
        news.setCategoryId(findById.get());

        given(newsRepository.findById(news.getId())).willReturn(findByIdNews);
        given(categoryRepository.findById(category.getIdCategories())).willReturn(findById);
        given((newsRepository.save(news))).willAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

        News updateNews = newsService.updateNews(news.getId(), newsDto);

        Assertions.assertNotNull(updateNews);

        verify(newsRepository).findById(anyLong());
        verify(newsRepository).save(any(News.class));
        verify(categoryRepository).findById(anyLong());
    }
}