package com.alkemy.ong.controller;

import com.alkemy.ong.dto.MemberDto;
import com.alkemy.ong.exception.NotFoundException;
import com.alkemy.ong.exception.PaginationMessage;
import com.alkemy.ong.mapper.MemberMapper;
import com.alkemy.ong.model.Member;
import com.alkemy.ong.repository.MemberRepository;
import com.alkemy.ong.security.service.UserDetailsCustomService;
import com.alkemy.ong.service.MemberService;
import com.amazonaws.services.simplesystemsmanagement.model.ParameterNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

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
public class MemberControllerTest {

    @Autowired
    private WebApplicationContext context;

    protected MockMvc mockMvc;

    @MockBean
    private MemberService memberService;

    @MockBean
    private UserDetailsCustomService userDetailsCustomService;

    @MockBean
    private MemberRepository memberRepository;

    @Autowired
    MemberMapper memberMapper;

    List<Member> memberList = new ArrayList<>();
    List<MemberDto> memberDtoList = new ArrayList<>();

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private PaginationMessage paginationMessage;

    private Member member;
    private MemberDto memberDto;
    private MemberDto memberDtoResponse;

    private WebRequest request = null;

    @MockBean
    private com.alkemy.ong.exception.MessagePag messagePag;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        member = new Member();
        member.setId(1L);
        member.setName("name");
        member.setFacebookUrl("facebookUrl");
        member.setInstagramUrl("instagramUrl");
        member.setLinkedinUrl("linkedinUrl");
        member.setImage("imageUrl");
        member.setDescription("description");
        member.setCreationDate(null);
        member.setUpdateDate(null);
        member.setDeleted(false);
        memberList.add(member);

        memberDto = new MemberDto();
        memberDto.setId(1L);
        memberDto.setName("name");
        memberDto.setFacebookUrl("facebookUrl");
        memberDto.setInstagramUrl("instagramUrl");
        memberDto.setLinkedinUrl("linkedinUrl");
        memberDto.setImage("imageUrl");
        memberDto.setDescription("description");
        memberDto.setCreationDate(null);
        memberDto.setUpdateDate(null);
        memberDtoList.add(memberDto);
    }

    @Test
    @WithMockUser(username = "facundo@mail.com", authorities = {"ROLE_ADMIN"})
    void createMember() throws Exception {
        when(memberService.saveMember(memberDto)).thenReturn(memberDto);
        mockMvc.perform(post("/members")
                   .contentType(APPLICATION_JSON)
                   .content(objectMapper.writeValueAsString(memberDto))
                   .with(user("admin").roles("ADMIN"))
                   .with(csrf()))
                   .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username = "facundo@mail.com", authorities = {"ROLE_ADMIN"})
    void createMemberBadRequest() throws Exception {
        memberDtoResponse = null;
        mockMvc.perform(post("/members")
                      .contentType(APPLICATION_JSON)
                      .content(objectMapper.writeValueAsString(memberDtoResponse)))
              .andExpect(status().isBadRequest());
       }


    @Test
    void createMemberForbidden() throws Exception {
           mockMvc.perform(post("/members")
                           .contentType(APPLICATION_JSON)
                           .with(csrf()))
                   .andExpect(status().isForbidden());
    }

    @WithMockUser(roles = "ADMIN")
    @Test
    void listMembers() throws Exception {

        WebRequest webRequest = mock(WebRequest.class);
        int page = 0;
        memberRepository.saveAll(memberList);
        Page membersPage = memberRepository.findAll(PageRequest.of(page, 10));
        when(memberService.getAllMembers(page, webRequest)).thenReturn(messagePag);
        mockMvc.perform(get("/members")
                        .contentType(APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().is(400));
    }

    @Test
    void getMemberForbidden() throws Exception {
        when(memberService.getAllMembers(0, request)).thenThrow((new ResponseStatusException(HttpStatus.FORBIDDEN)));
        mockMvc.perform(get("/members")
                        .contentType(APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isForbidden());
    }


    @Test
    @WithMockUser(username = "facundo@mail.com", authorities = {"ROLE_ADMIN"})
    void updateMember() throws Exception {
        Long id = 1L;
        when(memberService.updateMember(id, memberDto)).thenReturn(memberDto);
        mockMvc.perform(put("/members/{id}", id)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(member))
                        .with(user("admin").roles("ADMIN"))
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "facundo@mail.com", authorities = {"ROLE_ADMIN"})
    void updateMemberNotFound() throws Exception {
        Long id = 999L;
        memberDto.setName("cualquiera");
        when(memberService.updateMember(id, memberDto)).thenThrow(new ParameterNotFoundException(""));
        //doThrow(NotFoundException.class).when(memberService).updateMember(id,memberDto)
        mockMvc.perform(put("/members" + id))
                        .andExpect(status().isNotFound());
    }

    @Test
    void updateMemberForbidden() throws Exception {
        Long id = 1L;
        mockMvc.perform(put("/members/{id}", id)
                        .contentType(APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "facundo@mail.com", authorities = {"ROLE_ADMIN"})
    void deleteMember() throws Exception {
        Long id = 1L;
        mockMvc.perform(MockMvcRequestBuilders.delete("/members/{id}", id)
                        .contentType(APPLICATION_JSON)
                        .with(user("admin").roles("ADMIN"))
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "facundo@mail.com", authorities = {"ROLE_ADMIN"})
    void deleteMemberNotFound() throws Exception {
        Long id = 999L;
        doThrow(NotFoundException.class).when(memberService).deleteMemberById(id);
        mockMvc.perform(MockMvcRequestBuilders.delete("/members/{id}", id))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteMemberForbidden() throws Exception {
        Long id = 1L;
        mockMvc.perform(MockMvcRequestBuilders.delete("/members/{id}", id)
                        .contentType(APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isForbidden());
    }
}

