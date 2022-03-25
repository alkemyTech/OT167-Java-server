package com.alkemy.ong.service.impl;


import com.alkemy.ong.model.Contact;
import com.alkemy.ong.repository.ContactRespository;
import com.alkemy.ong.service.ContactService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ContactServiceImpl implements ContactService {
    private final ContactRespository contactRespository;
    @Override
    public Contact saveContact(Contact contact) {
        return contactRespository.save(contact);
    }
}
