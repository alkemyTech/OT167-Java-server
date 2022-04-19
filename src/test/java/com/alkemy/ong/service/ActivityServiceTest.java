package com.alkemy.ong.service;


import com.alkemy.ong.dto.ActivityDto;
import com.alkemy.ong.exception.NotFoundException;
import com.alkemy.ong.mapper.ActivityMapper;
import com.alkemy.ong.model.Activity;
import com.alkemy.ong.repository.ActivityRepository;
import com.alkemy.ong.service.impl.ActivityServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.Silent.class)
public class ActivityServiceTest {

    @InjectMocks
    private ActivityServiceImpl activityServiceTest;

    @Mock
    private ActivityRepository activityRepository;

    @Mock
    private ActivityMapper activityMapper;

    @Mock
    private PhotoService photoService;

    @Autowired
    private MessageSource messageSource;

    Activity activity;
    ActivityDto activityDto;

    MockMultipartFile image ;

    @BeforeEach
    void setUp(){

        activity = new Activity(1L,"name","content",null,null,null);
        activityDto = new ActivityDto();
        activityDto.setName("name");
        activityDto.setContent("content");

        image = new MockMultipartFile(
                "image",
                "image.jpeg",
                MediaType.IMAGE_JPEG_VALUE,
                "<<jpeg data>>".getBytes(StandardCharsets.UTF_8));

    }

    @Test
    void createActivityTest() throws IOException {

        String reponse = "image.jpeg";

        when(photoService.uploadImage(image)).thenReturn(reponse);
        when(activityMapper.activityDtoToModel(activityDto)).thenReturn(activity);
        when(activityRepository.save(activity)).thenReturn(activity);
        when(activityMapper.activityToDTO(activity)).thenReturn(activityDto);

        ActivityDto activitySaved = activityServiceTest.createActivity(activityDto,image);

        assertThat(activitySaved).isNotNull();

        verify(activityRepository).save(any(Activity.class));
    }

    @Test
    void updateTheNameInAnActivity(){

        when(activityRepository.findById(1L)).thenReturn(Optional.of(activity));
        when(activityRepository.save(activity)).thenReturn(activity);
        when(activityMapper.activityToDTO(activity)).thenReturn(activityDto);

        ActivityDto activityDtoUpdated = activityServiceTest.update(1L,activityDto);

        assertThat(activityDtoUpdated).isNotNull();

    }


    //---------- consultar pruebas ------------------
    @Test
    void createActivityButTheNameIsBlank() throws IOException {

        String reponse = "image.jpeg";
        activityDto.setName("");

        when(photoService.uploadImage(image)).thenReturn(reponse);
        when(activityMapper.activityDtoToModel(activityDto)).thenReturn(activity);
        when(activityRepository.save(activity)).thenReturn(activity);
        when(activityMapper.activityToDTO(activity)).thenReturn(activityDto);

        //ActivityDto activitySaved = activityServiceTest.createActivity(activityDto,image);

        Exception exeption = assertThrows(NullPointerException.class, () -> activityServiceTest.createActivity(activityDto,image));

        assertTrue(exeption.getMessage().contains(messageSource.getMessage("name.not.null", null, Locale.ENGLISH)));
    }

    @Test
    void updateAnActivityButItDoesNotExistInDataBase(){

        Exception exeption = assertThrows(NotFoundException.class, () -> activityServiceTest.update(3L,activityDto));

        assertTrue(exeption.getMessage().contains(messageSource.getMessage("id.not.found", null, Locale.ENGLISH)));


    }

    //-----------------------------------------------
}
