package com.alkemy.ong.controller;
import com.alkemy.ong.dto.ContactDto;
import com.alkemy.ong.exception.MessageInfo;
import com.alkemy.ong.service.ContactService;
import com.alkemy.ong.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.validation.Valid;
import java.util.List;
import java.util.Locale;

@RestController
@RequiredArgsConstructor
@RequestMapping("/contacts")
public class ContactController {

    private final EmailService emailService;
    private final MessageSource messageSource;
    @Autowired
    private ContactService contactService;

    @PostMapping("/register")
    public ResponseEntity<?> contact(@Valid @RequestBody ContactDto contactDto, WebRequest request) {
        contactService.save(contactDto);
        emailService.sendEmail(
                messageSource.getMessage("contact.email.subject", new Object[]{contactDto.getName()}, Locale.ENGLISH),
                contactDto.getEmail(),
                messageSource.getMessage("contact.email.message",null, Locale.ENGLISH));
        return ResponseEntity.status(HttpStatus.CREATED).body(new MessageInfo(
                messageSource.getMessage("contact.registered.successfully",null, Locale.ENGLISH),
                HttpStatus.CREATED.value(),
                ((ServletWebRequest)request).getRequest().getRequestURI()));
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<List<ContactDto>> getAllContacts(){
        return ResponseEntity.ok().body(contactService.getAllContacts());
    }
}
