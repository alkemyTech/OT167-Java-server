package com.alkemy.ong.ActivityTest;


import com.alkemy.ong.dto.ActivityDto;
import com.alkemy.ong.mapper.ActivityMapper;
import com.alkemy.ong.model.Activity;
import com.alkemy.ong.repository.ActivityRepository;
import com.alkemy.ong.service.ActivityService;
import com.alkemy.ong.service.PhotoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
/*@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.Silent.class)*/
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@TestPropertySource(locations = "")
public class ActivityServiceTest {

    @Autowired
    private WebApplicationContext context;
    protected MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private ActivityService activityService;

    @Mock
    private ActivityRepository activityRepository;

    @Autowired
    private ActivityMapper activityMapper;

    @MockBean
    private PhotoService photoService;

    private MultipartFile image;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        MockMultipartFile image =
                new MockMultipartFile(
                        "image",
                        "image.jpeg",
                        MediaType.IMAGE_JPEG_VALUE,
                        "<<jpeg data>>".getBytes(StandardCharsets.UTF_8));



    }

    @Test
    void createActivityTest() throws IOException {

        Activity activity = new Activity(null,"name","content","image.jpeg",null,null);
        ActivityDto activityDto = activityMapper.activityToDTO(activity);


        when(photoService.uploadImage(image)).thenReturn("image.jpeg");
        when(activityRepository.save(activity)).thenAnswer(invocation -> invocation.getArgument(0));

        ActivityDto activitySaved = activityService.createActivity(activityDto,image);

        assertThat(activitySaved).isNotNull();

        verify(activityRepository).save(any(Activity.class));
    }

    @Test
    void theActivityHasNameAndContent(){

    }

    @Test
    void TheActivityHasNotAName(){

    }

    @Test
    void updateTheNameInAnActivity(){

    }

}
