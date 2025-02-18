package com.example.Task_springboot.dto;

import com.example.Task_springboot.enums.UserRole;
import lombok.Data;

@Data
public class AuthenticationResponse {

    private String jwt;
    private Long userId;
    private UserRole userRole;
}
