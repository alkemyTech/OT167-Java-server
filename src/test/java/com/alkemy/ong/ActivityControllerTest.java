package com.alkemy.ong;

import com.alkemy.ong.dto.ActivityDto;
import com.alkemy.ong.model.Activity;
import com.alkemy.ong.service.ActivityService;
import jdk.net.SocketFlow;
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
import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
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

    private ArrayList<Activity> activities = new ArrayList<>();

    private ActivityDto activityDto;
    private Activity activity;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
        Activity activity = new Activity(Long.valueOf(1),"name1","content1","image",null,null);
        activities.add(activity);

        ActivityDto activityDto = new ActivityDto();
        activityDto.setId(1L);
        activityDto.setName("name2");
        activityDto.setContent("content2");
        activityDto.setImage("image");
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    void anActivityInformationIsUpdatedCorrectly() throws Exception {
        Long id = 1L;
        when(activityService.update(id,activityDto)).thenReturn(activityDto);

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