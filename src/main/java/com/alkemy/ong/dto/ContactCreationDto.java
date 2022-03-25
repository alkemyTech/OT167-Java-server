package com.alkemy.ong.dto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class ContactCreationDto {
    private String name;
    private int phone;
    private String email;
    private String message;
}
