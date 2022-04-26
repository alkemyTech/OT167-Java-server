package com.alkemy.ong.service;

import com.alkemy.ong.dto.TestimonialDto;
import com.alkemy.ong.exception.MessagePag;
import org.springframework.web.context.request.WebRequest;

public interface TestimonialService {

    public void save(TestimonialDto testimonialDto);

    TestimonialDto update(Long id, TestimonialDto testimonialDto);

    void delete(Long id);

    MessagePag findAllPag(int page, WebRequest request);
}
