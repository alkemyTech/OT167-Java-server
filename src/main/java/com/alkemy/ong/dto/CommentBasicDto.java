package com.alkemy.ong.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@RequiredArgsConstructor

public class CommentBasicDto {
    private Long id;
    private LocalDate creationDate;
    private String body;
}