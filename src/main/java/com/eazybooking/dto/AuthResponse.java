package com.eazybooking.dto;

import lombok.*;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Setter
public class AuthResponse {
    private String token;
    private String message;
    private int status;
}
