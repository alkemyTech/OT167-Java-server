package com.alkemy.ong.controller;
import com.alkemy.ong.dto.TestimonialDto;
import com.alkemy.ong.exception.NotFoundException;
import com.alkemy.ong.mapper.TestimonialMapper;
import com.alkemy.ong.model.Testimonial;
import com.alkemy.ong.repository.TestimonialRepository;
import com.alkemy.ong.service.TestimonialService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.mock.mockito.MockBean;
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

import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@TestPropertySource(locations = "")
class TestimonialControllerTest {
    @MockBean
    private TestimonialRepository testimonialRepository;
    @Autowired
    private WebApplicationContext context;
    protected MockMvc mockMvc;
    @MockBean
    private TestimonialService testimonialService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private TestimonialMapper testimonialMapper;
    List<Testimonial> testimonialList = new ArrayList<>();
    Testimonial testimonial1;
    Testimonial testimonial2;
    TestimonialDto testimonialDto1;
    TestimonialDto testimonialDto2;
    @MockBean
    private com.alkemy.ong.exception.MessagePag messagePag;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
        testimonial1 = new Testimonial(1L, "andres testimonial", "imageUrl", "content tesminonial", null, null, false);
        testimonial2 = new Testimonial(2L, "andres testimonial", "imageUrl", "content tesminonial", null, null, false);
        testimonialList.add(testimonial1);
        testimonialList.add(testimonial2);
        testimonialDto1 = testimonialMapper.testimonial2DTO(testimonial1);
        testimonialDto2 = testimonialMapper.testimonial2DTO(testimonial1);
    }
    // Delete testimonials by id
    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteUser() throws Exception {
        Long id = 1L;
        mockMvc.perform(MockMvcRequestBuilders.delete("/testimonials/{id}", id)
                        .contentType(APPLICATION_JSON)
                        .with(user("admin").roles("ADMIN"))
                        .with(csrf()))
                .andExpect(status().isOk());
    }
    // Error  delete testimonials by id
    @Test
    @WithMockUser(roles = "ADMIN")
    void return404WhenDeletingNonExistingTestimonial    () throws Exception {
        Long id = 99L;
        doThrow(NotFoundException.class).when(testimonialService).delete(id);
        mockMvc.perform(MockMvcRequestBuilders.delete("/testimonials/{id}", id))
                .andExpect(status().isNotFound());
    }
    //    Testimonial save
    @Test
    @WithMockUser(roles = "ADMIN")
    void saveTestimonial() throws Exception {
        mockMvc.perform(post("/testimonials")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testimonialDto1))
                        .with(user("admin").roles("ADMIN"))
                        .with(csrf()))
                .andExpect(status().isCreated());
    }
    //    Testimonial save error.
    @Test
    @WithMockUser(roles = "ADMIN")
    void saveErrorTestimonial() throws Exception {
        testimonialDto1 = null;
        mockMvc.perform(post("/testimonials")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testimonialDto1))
                        .with(user("admin").roles("ADMIN"))
                        .with(csrf()))
                .andExpect(status().isBadRequest());
    }
    // Update testimonial with admin
    @Test
    @WithMockUser(roles = "ADMIN")
    void updateTestimonial() throws Exception {
        Long id = 1L;
        when(testimonialService.update(id, testimonialDto1)).thenReturn(testimonialDto1);
        mockMvc.perform(put("/testimonials/{id}", id)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testimonialDto1))
                        .with(user("admin").roles("ADMIN"))
                        .with(csrf()))
                .andExpect(status().isOk());
    }
    // Error update testimonial not found by id
    @Test
    @WithMockUser(roles = "ADMIN")
    void updateTestimonialNotFound() throws Exception {
        Long id = 44L;
        when(testimonialService.update(id, testimonialDto1)).thenThrow(new NotFoundException(""));
        mockMvc.perform(put("/testimonials" + id))
                .andExpect(status().isNotFound());
    }
    //   Find by page 0 with login
    @Test
    @WithMockUser(roles = "ADMIN")
    void findTestimonialPag0WithLogin()  throws Exception {
        MockHttpServletRequest servletRequest = new MockHttpServletRequest();
        servletRequest.setRequestURI("/testimonials");
        WebRequest webRequest = new ServletWebRequest(servletRequest);
        int page = 0;
        when(testimonialService.findAllPag(page, webRequest)).thenReturn(messagePag);
        mockMvc.perform(get("/testimonials?page=0")
                        .contentType(APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    //   Try find with page 0 withouth login (403 Error)
    @Test
    void findTestimonialPagWithouthLogin()  throws Exception {
        MockHttpServletRequest servletRequest = new MockHttpServletRequest();
        servletRequest.setRequestURI("/testimonials");
        WebRequest webRequest = new ServletWebRequest(servletRequest);
        int page = 0;
        when(testimonialService.findAllPag(page, webRequest)).thenReturn(messagePag);
        mockMvc.perform(get("/testimonials?page=0")
                        .contentType(APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().is(403));
    }
    //    Find empty page with login
    @Test
    @WithMockUser(roles = "ADMIN")
    void findTestimonialPagEmptytWithLogin()  throws Exception {
        MockHttpServletRequest servletRequest = new MockHttpServletRequest();
        servletRequest.setRequestURI("/testimonials");
        WebRequest webRequest = new ServletWebRequest(servletRequest);
        int page = 444;
        when(testimonialService.findAllPag(page, webRequest)).thenReturn(messagePag);
        mockMvc.perform(get("/testimonials?page=0")
                        .contentType(APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isOk());
    }
}