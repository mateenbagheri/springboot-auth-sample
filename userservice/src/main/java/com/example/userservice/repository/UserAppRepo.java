package com.example.userservice.repository;

import com.example.userservice.domain.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAppRepo extends JpaRepository<UserApp, Long> {
    UserApp findByUsername(String username);
}
