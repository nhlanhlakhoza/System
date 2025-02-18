package com.example.Task_springboot.dto;

import com.example.Task_springboot.enums.UserRole;
import lombok.Data;

@Data
public class UserDto {
    private  Long id;
    private String name;
    private String email;
    private String password;
    private UserRole userRole;
}
