package com.alkemy.ong.service;

import com.alkemy.ong.dto.SlideDto;
import com.alkemy.ong.dto.SlideUpdateDto;
public interface SlideService {
    SlideDto updateSlide(Long id, SlideUpdateDto sliceDto);
    SlideDto saveSlide(SlideDto slideDto);
    void setOrgInSlide(Long idSlide, String nameOrg);
}
