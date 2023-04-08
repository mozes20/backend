package com.mozes.backend.dto;

import lombok.Data;

@Data
public class UserResponsDto {
    private int id;
    private String address;
    private String email;
    private String name;
    private String username;
}
