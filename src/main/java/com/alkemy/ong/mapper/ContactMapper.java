package com.alkemy.ong.mapper;
import com.alkemy.ong.dto.ContactCreationDto;
import com.alkemy.ong.model.Contact;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class ContactMapper {
    public ContactCreationDto contactToDto (Contact contact){
        ContactCreationDto contactCreationDto = new ContactCreationDto();
        contactCreationDto.setName(contact.getName());
        contactCreationDto.setEmail(contact.getEmail());
        contactCreationDto.setPhone(contact.getPhone());
        contactCreationDto.setMessage(contact.getMessage());
        return contactCreationDto;
    }
    public List<ContactCreationDto> contactListToDtoList (List<Contact> contactList){
        List<ContactCreationDto> creationDtoList = new ArrayList<>();
        contactList.forEach(cont -> creationDtoList.add(contactToDto(cont)));
        return creationDtoList;
    }
    public Contact creationContFromContactDto(ContactCreationDto dto) {
        Contact contact = new Contact();
        contact.setName(dto.getName());
        contact.setEmail(dto.getEmail());
        contact.setPhone(dto.getPhone());
        contact.setMessage(dto.getMessage());
        return contact;
    }
}
