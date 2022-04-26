package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.TestimonialDto;
import com.alkemy.ong.exception.MessagePag;
import com.alkemy.ong.exception.NotFoundException;
import com.alkemy.ong.exception.PaginationMessage;
import com.alkemy.ong.mapper.TestimonialMapper;
import com.alkemy.ong.model.Testimonial;
import com.alkemy.ong.repository.TestimonialRepository;
import com.alkemy.ong.service.TestimonialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.context.request.WebRequest;

@Service
public class TestimonialServiceImpl implements TestimonialService {
    
    private static final int SIZE_PAG_10 = 10;
    @Autowired
    private PaginationMessage paginationMessage;
    private WebRequest request;
    
    @Autowired
    private TestimonialMapper testimonialMapper;
    
    @Autowired
    private TestimonialRepository testimonialRepository;

    @Autowired
    private MessageSource messageSource;
    
    @Override
    public void save(TestimonialDto testimonialDto) {
        Testimonial testimonial = testimonialMapper.testimonialDto2Entity(testimonialDto);
        Testimonial testimonialSaved = testimonialRepository.save(testimonial);
    }

    public TestimonialDto update(Long id, TestimonialDto testimonialDto) {
        Optional<Testimonial> testimonial = this.testimonialRepository.findById(id);
        if (!testimonial.isPresent()) {
            throw new NotFoundException(messageSource.getMessage("testimonial.not.found", null, Locale.ENGLISH));
        }
        this.testimonialMapper.activityRefreshValues(testimonial.get(), testimonialDto);
        Testimonial testimonialSaved = this.testimonialRepository.save(testimonial.get());
        TestimonialDto result = this.testimonialMapper.testimonial2DTO(testimonialSaved);
        return result;
    }

    public void delete(Long id) {
        Optional<Testimonial> news = this.testimonialRepository.findById(id);
        if (!news.isPresent()) {
            throw new NotFoundException(messageSource.getMessage
                    ("testimonial.not.found", null, Locale.ENGLISH));
        }
        testimonialRepository.deleteById(id);
    }

    @Override
    public MessagePag findAllPag(int page, WebRequest request) {
        Page testimonialPage = testimonialRepository.findAll(PageRequest.of(page, SIZE_PAG_10));
        return paginationMessage.messageInfo(testimonialPage, testimonialMapper.listNewsDto(testimonialPage.getContent()), request);
    }
}