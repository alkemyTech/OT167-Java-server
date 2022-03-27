package com.alkemy.ong.controller;
import com.alkemy.ong.dto.ContactCreationDto;
import com.alkemy.ong.exception.MessageInfo;
import com.alkemy.ong.mapper.ContactMapper;
import com.alkemy.ong.model.Contact;
import com.alkemy.ong.service.ContactService;
import com.alkemy.ong.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.validation.Valid;
import java.util.Locale;

@RestController
@RequiredArgsConstructor
@RequestMapping("/contact")
public class ContactController {

    private final EmailService emailService;
    private final MessageSource messageSource;
    @Autowired
    private ContactService contactService;
    @Autowired
    private ContactMapper contactMapper;

    @PostMapping("/register")
    public ResponseEntity<?> contact(@Valid @RequestBody ContactCreationDto contactDto, WebRequest request) {
        Contact contact = contactMapper.creationContFromContactDto(contactDto);
        contactService.saveContact(contact);
            emailService.sendEmail(
                    messageSource.getMessage("email.subject", new Object[]{contact.getName()}, Locale.ENGLISH),
                    contact.getEmail(),
                    messageSource.getMessage("email.message",null, Locale.ENGLISH));
        return ResponseEntity.status(HttpStatus.CREATED).body(new MessageInfo(messageSource.getMessage("contact.registered.successfully", null, Locale.ENGLISH), HttpStatus.CREATED.value(), ((ServletWebRequest)request).getRequest().getRequestURI()));
    }
}
