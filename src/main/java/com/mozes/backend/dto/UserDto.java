package com.mozes.backend.dto;

import lombok.Data;

@Data
public class UserDto {
    private String address;
    private String email;
    private String name;
    private String password;
    private String username;
    private UserRoleDto userRole;
}
