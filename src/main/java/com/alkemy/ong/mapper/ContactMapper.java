package com.alkemy.ong.mapper;

import com.alkemy.ong.dto.ContactDto;
import com.alkemy.ong.model.Contact;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ContactMapper {

    public Contact contactDto2Contact(ContactDto dto){
        Contact contact = new Contact();
        contact.setName(dto.getName());
        contact.setPhone(dto.getPhone());
        contact.setEmail(dto.getEmail());
        contact.setMessage(dto.getMessage());
        return contact;
    }

    public ContactDto contacto2ContactDto(Contact contact){
        ContactDto contactDto = new ContactDto();
        contactDto.setId(contact.getId());
        contactDto.setName(contact.getName());
        contactDto.setPhone(contact.getPhone());
        contactDto.setEmail(contact.getEmail());
        contactDto.setMessage(contact.getMessage());
        return contactDto;
    }

    public List<ContactDto> contactList2DtoList(List<Contact> contacts) {
        List<ContactDto> dtos = new ArrayList<>();
        for (Contact contact:contacts){
            dtos.add(this.contacto2ContactDto(contact));
        }
        return dtos;
    }

    public List<Contact> contactDtoList2ContactList(List<ContactDto> contactDtos) {
        List<Contact> contacts = new ArrayList<>();
        for (ContactDto contactDto: contactDtos){
            contacts.add(this.contactDto2Contact(contactDto));
        }
        return contacts;
    }

}
