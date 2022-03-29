package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.ContactDto;
import com.alkemy.ong.exception.NotFoundException;
import com.alkemy.ong.mapper.ContactMapper;
import com.alkemy.ong.model.Contact;
import com.alkemy.ong.repository.ContactRepository;
import com.alkemy.ong.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactMapper contactMapper;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private final MessageSource messageSource;

    public ContactDto save(ContactDto dto) {
        Contact contact = contactMapper.contactDto2Contact(dto);
        Contact contactSaved = contactRepository.save(contact);
        ContactDto result = contactMapper.contacto2ContactDto(contactSaved);
        return result;
    }

    public List<ContactDto> getAllContacts() {
        List<Contact> contacts = contactRepository.findAll();
        List<ContactDto> result = contactMapper.contactList2DtoList(contacts);
        return result;
    }

    public void delete(Long id) {
        Optional<Contact> contact = this.contactRepository.findById(id);
        if (!contact.isPresent()) {
            throw new NotFoundException(messageSource.getMessage
                    ("contact.not.found", null, Locale.ENGLISH));
        }
    }
}
