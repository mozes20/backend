package com.mozes.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

@Data
public class AuthenticationResponse {
    private final String jwt;
}
