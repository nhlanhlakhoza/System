package com.example.Task_springboot.repositories;

import com.example.Task_springboot.entities.User;
import com.example.Task_springboot.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findFirstByEmail(String username);

    Optional<User> findByUserRole(UserRole userRole);
}
