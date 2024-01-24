package com.redbakery.redbakeryservice.service;

import com.redbakery.redbakeryservice.dto.request.RefreshTokenRequestDto;
import com.redbakery.redbakeryservice.dto.request.UserLoginRequestDto;
import com.redbakery.redbakeryservice.dto.request.UserSaveRequestDto;
import com.redbakery.redbakeryservice.dto.response.RefreshTokenResponseDto;
import com.redbakery.redbakeryservice.dto.response.UserLoginResponseDto;
import com.redbakery.redbakeryservice.dto.response.UserResponseDto;

public interface AuthenticationService {
    UserLoginResponseDto UserLogin(UserLoginRequestDto request);

    UserLoginResponseDto GetRefreshToken(RefreshTokenRequestDto request);
}
