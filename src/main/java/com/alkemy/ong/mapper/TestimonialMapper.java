package com.alkemy.ong.mapper;

import com.alkemy.ong.dto.TestimonialDto;
import com.alkemy.ong.model.Testimonial;
import java.time.LocalDate;
import org.springframework.stereotype.Component;

@Component
public class TestimonialMapper {

    public Testimonial testimonialDto2Entity (TestimonialDto testimonialDto){ 
        Testimonial testimonial = new Testimonial();
        testimonial.setName(testimonialDto.getName());
        testimonial.setContent(testimonialDto.getContent());
        testimonial.setImage(testimonialDto.getImage());
        testimonial.setCreationDate(LocalDate.now());
        return testimonial;
    }


    public TestimonialDto testimonial2DTO (Testimonial testimonial){
        TestimonialDto testimonialDto = new TestimonialDto();
        testimonialDto.setId(testimonial.getId());
        testimonialDto.setName(testimonial.getName());
        testimonialDto.setImage(testimonial.getImage());
        testimonialDto.setContent(testimonial.getContent());

        return testimonialDto;
    }

    public void activityRefreshValues(Testimonial testimonial, TestimonialDto testimonialDto) {
        testimonial.setName(testimonialDto.getName());
        testimonial.setImage(testimonialDto.getImage());
        testimonial.setContent(testimonialDto.getContent());
    }
}
