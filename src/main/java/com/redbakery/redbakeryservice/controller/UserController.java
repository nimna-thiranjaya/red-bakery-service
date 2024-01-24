package com.redbakery.redbakeryservice.controller;

import com.redbakery.redbakeryservice.common.ApplicationRoute;
import com.redbakery.redbakeryservice.common.CommonResponse;
import com.redbakery.redbakeryservice.dto.request.UserSaveRequestDto;
import com.redbakery.redbakeryservice.dto.response.UserResponseDto;
import com.redbakery.redbakeryservice.service.AuthenticationService;
import com.redbakery.redbakeryservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApplicationRoute.User.Root)
public class UserController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @PostMapping(ApplicationRoute.User.Save)
    public ResponseEntity<CommonResponse> Register(@RequestBody @Valid UserSaveRequestDto userSaveRequestDto) {
        ResponseEntity<CommonResponse> response = null;

        UserResponseDto user = userService.register(userSaveRequestDto);

        response = new ResponseEntity<CommonResponse>(
                new CommonResponse(true, "User registered", user),
                HttpStatus.CREATED
        );

        return response;
    }
}
