package com.alkemy.ong.controller;

import com.alkemy.ong.dto.CategoryDto;
import com.alkemy.ong.dto.NewsDto;
import com.alkemy.ong.exception.NotFoundException;
import com.alkemy.ong.mapper.NewsMapper;
import com.alkemy.ong.model.News;
import com.alkemy.ong.repository.NewsRepository;
import com.alkemy.ong.service.NewsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureJsonTesters
@SpringBootTest
@AutoConfigureMockMvc
public class NewsControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private NewsService newsService;

    @MockBean
    private NewsRepository newsRepository;

    @Autowired
    private JacksonTester<NewsDto> jsonNewsDTO;

    @Autowired
    private NewsMapper newsMapper;

    @Autowired
    private WebApplicationContext context;
    protected MockMvc mockMvc;

    private News news;
    private NewsDto newsDto;
    private NewsDto newsDtoResponse;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
        news = new News(null, "name", "content", "image", null,
                false, null, null);

        newsDtoResponse = newsMapper.newsEntity2Dto(news);

        newsDto = new NewsDto();
        newsDto.setName("name");
        newsDto.setContent("content");
        newsDto.setImage("image");
    }

    @Test
    @WithUserDetails(value = "pablo@mail.com")
    void canRetrieveByIdWhenExistsWithAdminRole() throws Exception {

        // given

        given(newsService.findById(1L)).willReturn(newsDto);

        // when
        MockHttpServletResponse response = mockMvc.perform(get("/news/1")
                        .accept(APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonNewsDTO
                .write(newsDto).getJson());
    }

    @Test
    @WithUserDetails(value = "diego@mail.com")
    void canRetrieveByIdWhenExistsWithUserRole() throws Exception {

        // given

        given(newsService.findById(1L)).willReturn(newsDto);

        // when
        MockHttpServletResponse response = mockMvc.perform(get("/news/1")
                        .accept(APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonNewsDTO
                .write(newsDtoResponse).getJson());
    }

    @Test
    void canRetrieveByIdWhenExistsWithoutRole() throws Exception {

        // given

        given(newsService.findById(1L)).willReturn(newsDto);

        // when
        MockHttpServletResponse response = mockMvc.perform(get("/news/1")
                        .accept(APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.FORBIDDEN.value());

    }

    /**
     * Ahora devolvemos un Objeto cuando ocurre un error, entonces ya no esta mas vacia la respuesta
     * @throws Exception
     */
    @Test
    @WithUserDetails(value = "pablo@mail.com")
    void canRetrieveByIdWhenDoesNotExists() throws Exception {

        given(newsService.findById(1L)).willThrow(new NotFoundException(""));

        // when
        MockHttpServletResponse response = mockMvc.perform(get("/news/1")
                        .accept(APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @WithUserDetails(value = "pablo@mail.com")
    void canCreateNewsWithAdminRole() throws Exception {

        // when
        MockHttpServletResponse response = mockMvc.perform(post("/news")
                        .contentType(APPLICATION_JSON)
                        .content(jsonNewsDTO
                                .write(newsDtoResponse)
                                .getJson()))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());

    }

    @Test
    @WithUserDetails(value = "diego@mail.com")
    void canCreateNewsWithUserRole() throws Exception {

        // when
        MockHttpServletResponse response = mockMvc.perform(post("/news")
                        .contentType(APPLICATION_JSON)
                        .content(jsonNewsDTO
                                .write(newsDtoResponse)
                                .getJson()))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());

    }

    @Test
    void createNewsWithoutRole() throws Exception {

        // when
        MockHttpServletResponse response = mockMvc.perform(post("/news")
                        .contentType(APPLICATION_JSON)
                        .content(jsonNewsDTO
                                .write(newsDtoResponse)
                                .getJson()))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.FORBIDDEN.value());

    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void canRetreiveWhenCreateNewsThrowBadRequest() throws Exception {

        newsDtoResponse = null;
        mockMvc.perform(post("/news")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newsDtoResponse))
                        .with(user("admin").roles("ADMIN"))
                        .with(csrf()))
                .andExpect(status().isBadRequest());

    }

    @Test
    @WithUserDetails(value = "pablo@mail.com")
    void canRetreiveWhenCreateNewsThrowNotFound() throws Exception {

        when(newsService.save(any())).thenThrow(NotFoundException.class);

        // when
        MockHttpServletResponse response = mockMvc.perform(post("/news")
                        .contentType(APPLICATION_JSON)
                        .content(jsonNewsDTO
                                .write(newsDtoResponse)
                                .getJson()))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());

    }

    @Test
    @WithUserDetails(value = "pablo@mail.com")
    void canUpdateNewsWithAdminRole() throws Exception {

        // when
        MockHttpServletResponse response = mockMvc.perform(put("/news/1")
                        .contentType(APPLICATION_JSON)
                        .content(jsonNewsDTO
                                .write(newsDtoResponse)
                                .getJson()))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

    }

    @Test
    @WithUserDetails(value = "diego@mail.com")
    void canUpdateNewsWithUserRole() throws Exception {

        // when
        MockHttpServletResponse response = mockMvc.perform(put("/news/1")
                        .contentType(APPLICATION_JSON)
                        .content(jsonNewsDTO
                                .write(newsDtoResponse)
                                .getJson()))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

    }

    @Test
    void updateNewsWithoutRole() throws Exception {

        // when
        MockHttpServletResponse response = mockMvc.perform(put("/news/1")
                        .contentType(APPLICATION_JSON)
                        .content(jsonNewsDTO
                                .write(newsDtoResponse)
                                .getJson()))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.FORBIDDEN.value());

    }

    @Test
    @WithUserDetails(value = "pablo@mail.com")
    void canRetreiveWhenUpdateNewsThrowBadRequest() throws Exception {

        newsDtoResponse = null;
        mockMvc.perform(put("/news/1")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newsDtoResponse))
                        .with(user("admin").roles("ADMIN"))
                        .with(csrf()))
                .andExpect(status().isBadRequest());

    }

    @Test
    @WithUserDetails(value = "pablo@mail.com")
    void canRetreiveWhenUpdateNewsThrowNotFound() throws Exception {

        when(newsService.updateNews(any(), any())).thenThrow(NotFoundException.class);

        // when
        MockHttpServletResponse response = mockMvc.perform(put("/news/1")
                        .contentType(APPLICATION_JSON)
                        .content(jsonNewsDTO
                                .write(newsDtoResponse)
                                .getJson()))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());

    }

    @Test
    @WithUserDetails(value = "pablo@mail.com")
    void canDeleteNewsWithAdminRole() throws Exception {

        // when
        MockHttpServletResponse response = mockMvc.perform(delete("/news/1")
                        .contentType(APPLICATION_JSON)
                        .content(jsonNewsDTO
                                .write(newsDtoResponse)
                                .getJson()))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

    }

    @Test
    @WithUserDetails(value = "diego@mail.com")
    void canDeleteNewsWithUserRole() throws Exception {

        // when
        MockHttpServletResponse response = mockMvc.perform(delete("/news/1")
                        .contentType(APPLICATION_JSON)
                        .content(jsonNewsDTO
                                .write(newsDtoResponse)
                                .getJson()))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

    }

    @Test
    void deleteNewsWithoutRole() throws Exception {

        // when
        MockHttpServletResponse response = mockMvc.perform(delete("/news/1")
                        .contentType(APPLICATION_JSON)
                        .content(jsonNewsDTO
                                .write(newsDtoResponse)
                                .getJson()))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.FORBIDDEN.value());

    }

    @Test
    @WithUserDetails(value = "pablo@mail.com")
    void canRetreiveWhenDeleteNewsThrowNotFound() throws Exception {

        // given
        doThrow(NotFoundException.class).when(newsService).delete(any());
        // when
        MockHttpServletResponse response = mockMvc.perform(delete("/news/1")
                        .contentType(APPLICATION_JSON)
                        .content(jsonNewsDTO
                                .write(newsDtoResponse)
                                .getJson()))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());

    }

}
