package com.alkemy.ong.service;

import com.alkemy.ong.dto.ContactDto;

import java.util.List;

public interface ContactService {

    ContactDto save(ContactDto dto);

    List<ContactDto> getAllContacts();

    void delete(Long id);

}
