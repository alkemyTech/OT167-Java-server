package com.alkemy.ong.controller;
import com.alkemy.ong.dto.CommentDto;
import com.alkemy.ong.dto.ContactDto;
import com.alkemy.ong.exception.MessageResponse;
import com.alkemy.ong.service.ContactService;
import com.alkemy.ong.service.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.validation.Valid;
import java.util.List;
import java.util.Locale;
@Tag(name = "Contact")
@RestController
@RequiredArgsConstructor
@RequestMapping("/contacts")
public class ContactController {

    private final EmailService emailService;
    private final MessageSource messageSource;
    @Autowired
    private ContactService contactService;
    @Autowired
    private MessageResponse messageResponse;

    @Operation(summary = "Add a new contact")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Contact save successfully",content = @Content),
            @ApiResponse(responseCode = "403", description = "You do not have the permissions to enter", content = @Content)                     
    })
    @PostMapping("/register")
    public ResponseEntity<?> contact(@Valid @RequestBody ContactDto contactDto, WebRequest request) {
        contactService.save(contactDto);
        emailService.sendEmail(
                messageSource.getMessage("contact.email.subject", new Object[]{contactDto.getName()}, Locale.ENGLISH),
                contactDto.getEmail(),
                messageSource.getMessage("contact.email.message",null, Locale.ENGLISH));
        return ResponseEntity.status(HttpStatus.CREATED).body(messageResponse.messageOk(messageSource.getMessage("contact.registered.successfully",null, Locale.ENGLISH), HttpStatus.CREATED.value(), request));
    }

    @Operation(summary = "Get all contacts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully get all contacts",content = @Content),
            @ApiResponse(responseCode = "403", description = "You do not have the permissions to enter", content = @Content)                     
    })
    @GetMapping
    public ResponseEntity<List<ContactDto>> getAllContacts(){
        return ResponseEntity.ok().body(contactService.getAllContacts());
    }

}
