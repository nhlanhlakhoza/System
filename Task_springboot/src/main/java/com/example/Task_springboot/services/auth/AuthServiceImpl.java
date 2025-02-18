package com.example.Task_springboot.services.auth;

import com.example.Task_springboot.dto.SignupRequest;
import com.example.Task_springboot.dto.UserDto;
import com.example.Task_springboot.entities.User;
import com.example.Task_springboot.enums.UserRole;
import com.example.Task_springboot.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService, CommandLineRunner {

    private final UserRepository userRepository;


    @Override

    public void run(String... args) throws Exception {
        createAnAdminAccount();
    }
    @Bean
    public void createAnAdminAccount() {
        Optional<User> optionalUser = userRepository.findByUserRole(UserRole.ADMIN);
        if (optionalUser.isEmpty()) {
            User user = new User();
            user.setEmail("admin@test.com");
            user.setName("admin");
            user.setPassword(new BCryptPasswordEncoder().encode("admin"));
            user.setUserRole(UserRole.ADMIN);
            userRepository.save(user);
            System.out.println("Admin account created successfully!");
        } else {
            System.out.println("Admin account already exists!");
        }
    }

    @Override
    public UserDto signupUser(SignupRequest signupRequest){
        User user = new User();
        user.setEmail(signupRequest.getEmail());
        user.setName(signupRequest.getName());
        user.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
        user.setUserRole(UserRole.EMPLOYEE);
        User createdUser =userRepository.save(user);

        return  createdUser.getUserDto();
    }

    @Override
    public boolean hasUserWithEmail(String email) {
        return userRepository.findFirstByEmail(email).isPresent();
    }
}
