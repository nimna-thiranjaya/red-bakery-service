package com.redbakery.redbakeryservice.controller;

import com.redbakery.redbakeryservice.common.ApplicationRoute;
import com.redbakery.redbakeryservice.common.CommonResponse;
import com.redbakery.redbakeryservice.dto.request.RefreshTokenRequestDto;
import com.redbakery.redbakeryservice.dto.request.UserLoginRequestDto;
import com.redbakery.redbakeryservice.dto.request.UserSaveRequestDto;
import com.redbakery.redbakeryservice.dto.response.RefreshTokenResponseDto;
import com.redbakery.redbakeryservice.dto.response.UserLoginResponseDto;
import com.redbakery.redbakeryservice.dto.response.UserResponseDto;
import com.redbakery.redbakeryservice.service.AuthenticationService;
import com.redbakery.redbakeryservice.service.UserService;
import jakarta.validation.Valid;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequiredArgsConstructor
@RequestMapping(ApplicationRoute.Authentication.Root)
public class AuthenticationController {
    private final  AuthenticationService authenticationService;
    @PostMapping(ApplicationRoute.Authentication.Login)
    public ResponseEntity<CommonResponse> UserLogin(@RequestBody @Valid UserLoginRequestDto request){
        ResponseEntity<CommonResponse> response = null;

        UserLoginResponseDto userLoginResponseDto = authenticationService.UserLogin(request);

        response = new ResponseEntity<CommonResponse>(
                new CommonResponse(true, "Login Successful!", userLoginResponseDto),
                HttpStatus.OK
        );

        return  response;
    }

    @PostMapping(ApplicationRoute.Authentication.RefreshToken)
    public ResponseEntity<CommonResponse> RefreshToken(@RequestBody RefreshTokenRequestDto request){
        ResponseEntity<CommonResponse> response = null;

        UserLoginResponseDto responseDto = authenticationService.GetRefreshToken(request);

        response = new ResponseEntity<CommonResponse>(
                new CommonResponse(true, "Token refresh successful!", responseDto),
                HttpStatus.OK
        );

        return  response;
    }
}
