package com.example.sotisproject.service;

import com.example.sotisproject.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService  extends UserDetailsService {
    User findByUsername(String username);
}
