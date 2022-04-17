package com.alkemy.ong;

import com.alkemy.ong.dto.ActivityDto;
import com.alkemy.ong.mapper.ActivityMapper;
import com.alkemy.ong.model.Activity;
import com.alkemy.ong.service.ActivityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
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
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;
    protected MockMvc mockMvc;

    @MockBean
    private ActivityService activityService;

    @Autowired
    private ActivityMapper activityMapper;

    private ArrayList<Activity> activities = new ArrayList<>();

    private ActivityDto activityDto;
    private Activity activity;
    private ActivityDto activityDtoResponse;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
        activity = new Activity(1L,"name1","content1","image1",null,null);
        //activities.add(activity);

        activityDtoResponse = activityMapper.activityToDTO(activity);

        activityDto = new ActivityDto();
        activityDto.setName("name2");
        activityDto.setContent("content2");
        activityDto.setImage("imag2");


    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void createAnActivity() throws Exception{

        MockMultipartFile image =
                new MockMultipartFile(
                        "image",
                        "image.jpeg",
                        MediaType.IMAGE_JPEG_VALUE,
                        "<<jpeg data>>".getBytes(StandardCharsets.UTF_8));

        when(activityService.createActivity(activityDto, image)).thenReturn(activityDto);

        mockMvc.perform(post("/activity/activities")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(activityDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void createActivityBadRequest() throws Exception{
        activityDtoResponse = null;

        mockMvc.perform(post("/activity/activities")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(activityDtoResponse))
                        .with(user("admin").roles("ADMIN"))
                        .with(csrf()))
                .andExpect(status().isBadRequest());
    }


    //consultar porque no lo toma
    @Test
    @WithMockUser(roles = "ADMIN")
    void anActivityInformationIsUpdatedCorrectly() throws Exception {
        Long id = 1L;

        when(activityService.update(id,activityDto)).thenReturn(activityDtoResponse);

        this.mockMvc.perform(put("/activity/{id}",id)
                .contentType(APPLICATION_JSON)
                .with(csrf()))
                .andExpect(status().is(200));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void anActivityInformationIsUpadatedButThereIsNoInfoInDataBase() throws Exception{

        Long id = 100L;
        activityDto = null;

        when(activityService.update(id,activityDto)).thenReturn(activityDto);

        this.mockMvc.perform(put("/activity/{id}",id))
                .andExpect(status().is(400));
    }

}