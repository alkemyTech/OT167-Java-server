package com.alkemy.ong.service.impl;
import com.alkemy.ong.dto.ContactDto;
import com.alkemy.ong.mapper.ContactMapper;
import com.alkemy.ong.model.Contact;
import com.alkemy.ong.repository.ContactRespository;
import com.alkemy.ong.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ContactServiceImpl implements ContactService {
    private final ContactRespository contactRespository;
    @Autowired
    private ContactMapper contactMapper;
    @Override
    public ContactDto save(ContactDto dto) {
        Contact contact = contactMapper.contactDto2Contact(dto);
        Contact contactSaved = contactRespository.save(contact);
        ContactDto result = contactMapper.contacto2ContactDto(contactSaved);
        return result;
    }
}
