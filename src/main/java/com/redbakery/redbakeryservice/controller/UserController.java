package com.redbakery.redbakeryservice.controller;

import com.redbakery.redbakeryservice.common.ApplicationRoute;
import com.redbakery.redbakeryservice.common.CommonResponse;
import com.redbakery.redbakeryservice.dto.AuthenticationTicketDto;
import com.redbakery.redbakeryservice.dto.request.UserSaveRequestDto;
import com.redbakery.redbakeryservice.dto.response.UserResponseDto;
import com.redbakery.redbakeryservice.service.AuthenticationService;
import com.redbakery.redbakeryservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(ApplicationRoute.User.GetProfile)
    ResponseEntity<CommonResponse> GetUserProfile (){
        ResponseEntity<CommonResponse> response = null;

        AuthenticationTicketDto authTicket = authenticationService.AuthenticationTicket();

        UserResponseDto user = userService.getUserProfile(authTicket.getUserId());

        response = new ResponseEntity<CommonResponse>(
                new CommonResponse(true, "", user),
                HttpStatus.OK
        );

        return response;

    }
}
