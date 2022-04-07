package com.alkemy.ong.service;

import com.alkemy.ong.dto.SlideDto;
import java.io.IOException;
import java.util.List;
import com.alkemy.ong.dto.SlideUpdateDto;

public interface SlideService {
    
    SlideDto createSlide(SlideDto slideDto)throws IOException;
    List<SlideDto> findAllSlide();
    SlideDto updateSlide(Long id, SlideUpdateDto slideDto);
    void setOrgInSlide(Long idSlide, String nameOrg);
    SlideDto findById(Long id);

}
