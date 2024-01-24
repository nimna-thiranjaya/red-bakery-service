package com.redbakery.redbakeryservice.service;

import com.redbakery.redbakeryservice.dto.request.UserLoginRequestDto;
import com.redbakery.redbakeryservice.dto.request.UserSaveRequestDto;
import com.redbakery.redbakeryservice.dto.response.UserLoginResponseDto;
import com.redbakery.redbakeryservice.dto.response.UserResponseDto;

public interface AuthenticationService {
    UserLoginResponseDto userLogin(UserLoginRequestDto request);
}
