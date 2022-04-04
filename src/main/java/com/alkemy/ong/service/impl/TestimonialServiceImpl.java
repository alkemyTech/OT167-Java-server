package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.TestimonialDto;
import com.alkemy.ong.mapper.TestimonialMapper;
import com.alkemy.ong.model.Testimonial;
import com.alkemy.ong.repository.TestimonialRepository;
import com.alkemy.ong.service.TestimonialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestimonialServiceImpl implements TestimonialService {
    @Autowired
    private TestimonialMapper testimonialMapper;
    
    @Autowired
    private TestimonialRepository testimonialRepository;
    
    @Override
    public void save(TestimonialDto testimonialDto) {
        Testimonial testimonial = testimonialMapper.testimonialDto2Entity(testimonialDto);
        Testimonial testimonialSaved = testimonialRepository.save(testimonial);
    }
}
