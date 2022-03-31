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
           
            
}
