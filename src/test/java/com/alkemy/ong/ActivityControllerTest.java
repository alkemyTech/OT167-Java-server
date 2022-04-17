package com.alkemy.ong;

import com.alkemy.ong.dto.ActivityDto;
import com.alkemy.ong.mapper.ActivityMapper;
import com.alkemy.ong.model.Activity;
import com.alkemy.ong.service.ActivityService;
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
import static org.springframework.http.MediaType.APPLICATION_JSON;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@TestPropertySource(locations = "")
public class ActivityControllerTest {


    @Autowired
    private WebApplicationContext context;
    protected MockMvc mockMvc;

    @MockBean
    private ActivityService activityService;

    @MockBean
    private ActivityMapper activityMapper;

    private ArrayList<Activity> activities = new ArrayList<>();

    private ActivityDto activityDto;
    private ActivityDto activityDto2;
    private Activity activity;
    private ActivityDto activityDtoResponse;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
        Activity activity = new Activity(Long.valueOf(1),"name1","content1",null,null,null);
        activities.add(activity);

        ActivityDto activityDtoResponse = new ActivityDto();

        ActivityDto activityDto = new ActivityDto();
        activityDto.setName("name2");
        activityDto.setContent("content2");

        ActivityDto activityDto2 = new ActivityDto();
        activityDto2.setId(1L);
        activityDto2.setName("name2");
        activityDto2.setContent("content2");
        activityDto2.setImage("image");


    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void createAnActivity() throws Exception{

        activityDtoResponse = activityMapper.activityToDTO(activity);

        when(activityService.createActivity(activityDto, null)).thenReturn(activityDtoResponse);

        mockMvc.perform(post("/activity/activities")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(activityDtoResponse))
                        .with(user("admin").roles("ADMIN"))
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void anActivityInformationIsUpdatedCorrectly() throws Exception {
        Long id = 1L;
        when(activityService.update(id,activityDto2)).thenReturn(activityDto);

        this.mockMvc.perform(put("/activity/{id}",id))
                .andExpect(status().is(200));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void anActivityInformationIsUpadatedButThereIsNoInfoInDataBase() throws Exception{

        Long id = 100L;

        when(activityService.update(id,activityDto)).thenReturn(activityDto);

        /*given(activityService.update(id,activity2)).willReturn(activityResponse);*/

        this.mockMvc.perform(put("/activity/{id}",id))
                .andExpect(status().is(400));
    }

}