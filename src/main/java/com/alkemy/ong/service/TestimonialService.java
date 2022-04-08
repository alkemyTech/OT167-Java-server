package com.alkemy.ong.service;

import com.alkemy.ong.dto.TestimonialDto;

public interface TestimonialService {

    public void save(TestimonialDto testimonialDto);

    TestimonialDto update(Long id, TestimonialDto testimonialDto);

    void delete(Long id);

}
