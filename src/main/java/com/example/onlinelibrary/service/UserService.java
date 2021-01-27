package com.example.onlinelibrary.service;

import com.example.onlinelibrary.dto.UserRegistrationDto;
import com.example.onlinelibrary.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;


public interface UserService extends UserDetailsService {
    User save(UserRegistrationDto registrationDto);
}


