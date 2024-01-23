package com.redbakery.redbakeryservice.service;

import com.redbakery.redbakeryservice.dto.request.UserSaveRequestDto;
import com.redbakery.redbakeryservice.dto.response.UserResponseDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {
    UserDetailsService userDetailService();

    public UserResponseDto register(UserSaveRequestDto userSaveRequestDto);
}
