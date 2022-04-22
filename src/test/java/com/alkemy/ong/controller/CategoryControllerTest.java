package com.alkemy.ong.controller;

import com.alkemy.ong.dto.CategoryDto;
import com.alkemy.ong.exception.NotFoundException;
import com.alkemy.ong.exception.PaginationMessage;
import com.alkemy.ong.mapper.CategoryMapper;
import com.alkemy.ong.model.Category;
import com.alkemy.ong.repository.CategoryRepository;
import com.alkemy.ong.service.impl.CategoryServiceImpl;
import com.amazonaws.services.simplesystemsmanagement.model.ParameterNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@TestPropertySource(locations = "")
public class CategoryControllerTest {

    @Autowired
    private WebApplicationContext context;

    protected MockMvc mockMvc;

    @MockBean
    private CategoryServiceImpl categoryService;

    @MockBean
    private CategoryRepository categoryRepository;

    @Autowired
    CategoryMapper categoryMapper;

    List<Category> categoryList = new ArrayList<>();
    List<String> categoryNameList = new ArrayList<>();
    List<CategoryDto> categoryDtoList = new ArrayList<>();

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private PaginationMessage paginationMessage;

    private Category category;
    private CategoryDto categoryDto;
    private CategoryDto categoryDtoResponse;

    private WebRequest request = null;

    @MockBean
    private com.alkemy.ong.exception.MessagePag messagePag;
    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        category = new Category();
        category.setIdCategories(1L);
        category.setName("name");
        category.setImage("imageUrl");
        category.setDescription("description");
        category.setCreationDate(null);
        category.setUpdateDate(null);
        category.setDeleted(false);
        categoryNameList = categoryService.getAllCategoriesByName();

        categoryDto = new CategoryDto();
        categoryDto.setId(1L);
        categoryDto.setName("name");
        categoryDto.setImage("imageUrl");
        categoryDto.setDescription("description");
        categoryDtoList.add(categoryDto);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getMemberListPage() throws Exception {
        MockHttpServletRequest servletRequest = new MockHttpServletRequest();
        servletRequest.setRequestURI("/categories");
        WebRequest webRequest = new ServletWebRequest(servletRequest);
        when(categoryService.getAllCategoriesByName()).thenReturn(categoryNameList);
        mockMvc.perform(get("/categories")
                        .contentType(APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    void getMemberForbidden() throws Exception {
        when(categoryService.getAllCategoriesByName()).thenThrow((new ResponseStatusException(HttpStatus.FORBIDDEN)));
        mockMvc.perform(get("/categories")
                        .contentType(APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void createCategory() throws Exception {
        when(categoryService.save(categoryDto)).thenReturn(categoryDto);
        mockMvc.perform(post("/categories")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryDto))
                        .with(user("admin").roles("ADMIN"))
                        .with(csrf()))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void createCategoryBadRequest() throws Exception {
        categoryDtoResponse = null;
        mockMvc.perform(post("/categories")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryDtoResponse)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createCategoryForbidden() throws Exception {
        mockMvc.perform(post("/categories")
                        .contentType(APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateCategoryNotFound() throws Exception {
        Long id = 999L;
        categoryDto.setName("cualquiera");
        when(categoryService.updateCategory(id, category)).thenThrow(new ParameterNotFoundException(""));
        mockMvc.perform(put("/categories" + id))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateCategoryForbidden() throws Exception {
        Long id = 1L;
        mockMvc.perform(put("/categories/{id}", id)
                        .contentType(APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteCategory() throws Exception {
        Long id = 1L;
        mockMvc.perform(MockMvcRequestBuilders.delete("/categories/{id}", id)
                        .contentType(APPLICATION_JSON)
                        .with(user("admin").roles("ADMIN"))
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteCategoryNotFound() throws Exception {
        Long id = 999L;
        doThrow(NotFoundException.class).when(categoryService).deleteCategoryById(id);
        mockMvc.perform(MockMvcRequestBuilders.delete("/categories/{id}", id))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteCategoryForbidden() throws Exception {
        Long id = 1L;
        mockMvc.perform(MockMvcRequestBuilders.delete("/categories/{id}", id)
                        .contentType(APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isForbidden());
    }
}
