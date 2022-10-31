package com.example.sotisproject.service;

import org.apache.catalina.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService  extends UserDetailsService {
    User findByUsername(String username);
}
