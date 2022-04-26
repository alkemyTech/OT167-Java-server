package com.alkemy.ong.controller;

import com.alkemy.ong.dto.OrganizationCreationDto;
import com.alkemy.ong.dto.OrganizationDto;
import com.alkemy.ong.dto.OrganizationSlideDto;
import com.alkemy.ong.mapper.OrganizationMapper;
import com.alkemy.ong.model.Organization;
import com.alkemy.ong.service.OrganizationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.*;


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
public class OrganizationControllerTest {

    @Autowired
    private WebApplicationContext context;

    protected MockMvc mockMvc;

    @MockBean
    private OrganizationService organizationService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    OrganizationMapper organizationMapper;

    private Organization organization;
    private OrganizationCreationDto organizationCreationDto;
    private OrganizationDto organizationDto;
    private OrganizationSlideDto organizationSlideDto;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        organization = new Organization();
        organization.setId(1L);
        organization.setName("name");
        organization.setImage("imageUrl");
        organization.setAddress("address");
        organization.setEmail("mail");
        organization.setPhone("phone");
        organization.setFacebookUrl("facebookUrl");
        organization.setInstagramUrl("instagramUrl");
        organization.setLinkedInUrl("linkedInUrl");



        organizationCreationDto = new OrganizationCreationDto();
        organizationCreationDto.setName("name");
        organizationCreationDto.setEmail("email");
        organizationCreationDto.setImage("imageUrl");
        organizationCreationDto.setAddress("address");
        organizationCreationDto.setPhone("phone");
        organizationCreationDto.setFacebookUrl("facebookUrl");
        organizationCreationDto.setInstagramUrl("instagramUrl");
        organizationCreationDto.setLinkedInUrl("linkedInUrl");

        organizationDto= organizationMapper.organizationToDto(organizationMapper.creationOrgFromOrganizationDto(organizationCreationDto));

        organizationSlideDto= organizationService.organizationSlide();

    }

    // CREATE ORGANIZATIONS
    @Test
    @WithMockUser(roles = "ADMIN")
    void createOrganization() throws Exception {
        when(organizationService.save(organizationCreationDto)).thenReturn(organizationDto);
        mockMvc.perform(post("/organization")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(organizationDto))
                        .with(user("admin").roles("ADMIN"))
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void createOrganizationBadRequest() throws Exception {
        organizationCreationDto=null;
        mockMvc.perform(post("/organization")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(organizationCreationDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createOrganizationFailBecauseUserNotAuthenticated() throws Exception {
        mockMvc.perform(post("/organization")
                        .contentType(APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isForbidden());
    }

    // EDIT ORGANIZATION
    @Test
    @WithMockUser(roles = "ADMIN")
    void editOrganization() throws Exception {
        when(organizationService.editOrganization(organizationCreationDto)).thenReturn(organizationDto);
        mockMvc.perform(get("/organization/public")
                        .contentType(APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isOk());

    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void editOrganizationBadRequest() throws Exception {
        organizationCreationDto=null;
        mockMvc.perform(put("/organization/public")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(organizationCreationDto)))
                .andExpect(status().isBadRequest());

    }


    //  GET ORGANIZATION SLIDE
    @Test
    @WithMockUser(roles = "ADMIN")
    void getOrganizationSlide() throws Exception {
        when(organizationService.organizationSlide()).thenReturn(organizationSlideDto);
        mockMvc.perform(get("/organization/public")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(organizationSlideDto)))
                .andExpect(status().isOk());
    }

    @Test
    void getOrganizationSlideFailBecauseUserNotAuthenticated() throws Exception {
        mockMvc.perform(get("/organization/public")
                        .contentType(APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isForbidden());
    }

}
