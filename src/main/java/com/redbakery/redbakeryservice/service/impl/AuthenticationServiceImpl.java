package com.redbakery.redbakeryservice.service.impl;

import com.redbakery.redbakeryservice.dto.AuthenticationTicketDto;
import com.redbakery.redbakeryservice.dto.request.RefreshTokenRequestDto;
import com.redbakery.redbakeryservice.dto.request.UserLoginRequestDto;
import com.redbakery.redbakeryservice.dto.request.UserSaveRequestDto;
import com.redbakery.redbakeryservice.dto.response.RefreshTokenResponseDto;
import com.redbakery.redbakeryservice.dto.response.UserLoginResponseDto;
import com.redbakery.redbakeryservice.dto.response.UserResponseDto;
import com.redbakery.redbakeryservice.exception.BadRequestException;
import com.redbakery.redbakeryservice.model.User;
import com.redbakery.redbakeryservice.repository.UserRepository;
import com.redbakery.redbakeryservice.service.AuthenticationService;
import com.redbakery.redbakeryservice.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final ModelMapper modelMapper;

    @Override
    public UserLoginResponseDto UserLogin(UserLoginRequestDto request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
        } catch (Exception e) {
            throw new BadRequestException("Invalid Email or Password!");
        }

        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new BadRequestException("Invalid Email or Password!"));

        String token = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshT(new HashMap<>(), user);

        UserResponseDto userResponseDto = modelMapper.map(user, UserResponseDto.class);

        UserLoginResponseDto response = new UserLoginResponseDto(
                token, refreshToken, userResponseDto
        );

        return response;
    }

    @Override
    public UserLoginResponseDto GetRefreshToken(RefreshTokenRequestDto request) {
        try{
            String email = jwtService.extractUserName(request.getToken());

            User user = userRepository.findByEmail(email).orElseThrow(
                    () -> new BadRequestException("Invalid Token!")
            );

            if (jwtService.isTokenValid(request.getToken(), user)) {
                String token = jwtService.generateToken(user);

                UserResponseDto userResponseDto = modelMapper.map(user, UserResponseDto.class);

                UserLoginResponseDto response = new UserLoginResponseDto(
                        token,
                        request.getToken(),
                        userResponseDto
                );

                return response;
            } else {
                throw new BadRequestException("Invalid Token!");
            }
        }catch (Exception e){
            throw new BadRequestException("Invalid Token!");
        }
    }

    @Override
    public AuthenticationTicketDto AuthenticationTicket() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            User user = userRepository.findByEmail(userDetails.getUsername()).get();

            AuthenticationTicketDto authTicket = modelMapper.map(user, AuthenticationTicketDto.class);

            authTicket.setUser(user);

            return authTicket;

        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }


}
