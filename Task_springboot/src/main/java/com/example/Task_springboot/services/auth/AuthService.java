package com.example.Task_springboot.services.auth;

import com.example.Task_springboot.dto.SignupRequest;
import com.example.Task_springboot.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {

 UserDto signupUser(SignupRequest signupRequest);
 boolean hasUserWithEmail(String email);
}
