package com.example.Task_springboot.dto;

import lombok.Data;

@Data
public class AuthenticationRequest {

    private String email;
    private String password;
}
