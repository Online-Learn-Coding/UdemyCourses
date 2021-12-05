package com.appsdeveloper.usersapi.oneureka.ui.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.appsdeveloper.usersapi.oneureka.ui.shared.UserDto;

public interface UsersService extends UserDetailsService {   //usersDetailsSErvice is being extended due to the configure message for authenticationManagerBuilder in WebSecurity class
 UserDto createUser(UserDto userDetails);
 UserDto getUserDetailsByEmail(String email);
}
