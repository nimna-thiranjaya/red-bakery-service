package com.redbakery.redbakeryservice.service;

import com.redbakery.redbakeryservice.dto.AuthenticationTicketDto;
import com.redbakery.redbakeryservice.dto.request.UserSaveRequestDto;
import com.redbakery.redbakeryservice.dto.request.UserUpdateRequestDto;
import com.redbakery.redbakeryservice.dto.response.UserResponseDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService {
    UserDetailsService userDetailService();

    public UserResponseDto register(UserSaveRequestDto userSaveRequestDto);

    UserResponseDto getUserProfile(Long userId);

    UserResponseDto updateUserProfile(Long userId, UserUpdateRequestDto userUpdateRequestDto);

    void deleteUserProfile(Long userId);

    List<UserResponseDto> getAllUsers();
}
