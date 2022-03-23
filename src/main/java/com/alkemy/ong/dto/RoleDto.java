package com.alkemy.ong.dto;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleDto {

    private Long id;
    private String name;
    private String description;
    private LocalDateTime createdDate;
    private List<UserRegisterRequest> users;
    private LocalDateTime modifiedDate;
    
}
