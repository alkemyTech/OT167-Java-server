package com.alkemy.ong.service;

import com.alkemy.ong.dto.SlideDto;
import java.io.IOException;
import java.util.List;


public interface SlideService {
    
    SlideDto createSlide(SlideDto slideDto)throws IOException;
    List<SlideDto> findAllSlide();
}
