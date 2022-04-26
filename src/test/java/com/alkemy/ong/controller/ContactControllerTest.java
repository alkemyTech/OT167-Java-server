package com.alkemy.ong.controller;

import com.alkemy.ong.dto.ContactDto;
import com.alkemy.ong.mapper.ContactMapper;
import com.alkemy.ong.model.Contact;
import com.alkemy.ong.service.ContactService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.server.ResponseStatusException;

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
public class ContactControllerTest {

    @Autowired
    private WebApplicationContext context;

    protected MockMvc mockMvc;

    @MockBean
    private ContactService contactService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    ContactMapper contactMapper;

    private Contact contact;
    private ContactDto contactDto;

    List<Contact> contactsEntityList = new ArrayList<>();
    List<ContactDto> contactDtoList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        contact = new Contact();
        contact.setId(1L);
        contact.setName("name");
        contact.setEmail("mail");
        contact.setMessage("mail");
        contact.setPhone("mail");


        contactDto = new ContactDto();
        contactDto.setId(1L);
        contactDto.setName("name");
        contactDto.setEmail("mail");
        contactDto.setMessage("menssage");
        contactDto.setPhone("phone");

        contactDtoList.add(contactDto);
        contactsEntityList.add(contact);

    }

    //== POST CONTACTS ==
    @Test
    @WithMockUser(roles = "ADMIN")
    void createContact() throws Exception {
        when(contactService.save(contactDto)).thenReturn(contactDto);
        mockMvc.perform(post("/contacts/register")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(contactDto))
                        .with(user("admin").roles("ADMIN"))
                        .with(csrf()))
                .andExpect(status().isCreated());

    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void createContactBadRequest() throws Exception {
        contactDto.setMessage(null);
        contactDto.setEmail(null);
        contactDto.setPhone(null);
        contactDto.setName(null);
        mockMvc.perform(post("/contacts/register")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(contactDto)))
                .andExpect(status().isBadRequest());


    }

    @Test
    void createContactFailBecauseUserNotAuthenticated() throws Exception {
        mockMvc.perform(post("/contacts/register")
                        .contentType(APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isForbidden());
    }

    //== GET CONTACTS ==
    @Test
    @WithMockUser(roles = "ADMIN")
    void getAllContact() throws Exception {
        when(contactService.getAllContacts()).thenReturn(contactMapper.contactList2DtoList(contactsEntityList));
        mockMvc.perform(get("/contacts")
                        .contentType(APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isOk());

    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getContactNotFound() throws Exception {
        contactsEntityList.add(0,null);
        when(contactService.getAllContacts()).thenThrow((new ResponseStatusException(HttpStatus.NOT_FOUND)));
        mockMvc.perform(get("/contacts")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(contactsEntityList)))
                .andExpect(status().isNotFound());
    }

    @Test
    void getContactFailBecauseUserNotAuthenticated() throws Exception {
        mockMvc.perform(get("/contacts")
                        .contentType(APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isForbidden());
    }

}
