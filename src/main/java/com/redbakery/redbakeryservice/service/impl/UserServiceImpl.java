package com.redbakery.redbakeryservice.service.impl;

import com.redbakery.redbakeryservice.common.WellKnownStatus;
import com.redbakery.redbakeryservice.dto.request.UserSaveRequestDto;
import com.redbakery.redbakeryservice.dto.request.UserUpdateRequestDto;
import com.redbakery.redbakeryservice.dto.response.UserResponseDto;
import com.redbakery.redbakeryservice.exception.BadRequestException;
import com.redbakery.redbakeryservice.exception.NotFoundException;
import com.redbakery.redbakeryservice.model.Role;
import com.redbakery.redbakeryservice.model.User;
import com.redbakery.redbakeryservice.repository.UserRepository;
import com.redbakery.redbakeryservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetailsService userDetailService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) {
                return userRepository.findByEmailAndStatus(username, WellKnownStatus.ACTIVE.getValue())
                        .orElseThrow(() -> new UsernameNotFoundException("User not found!"));
            }
        };
    }

    @Override
    public UserResponseDto register(UserSaveRequestDto userSaveRequestDto) {
        // Check if email already exist
        Boolean isEmailExist = userRepository.existsUserByEmail(userSaveRequestDto.getEmail());

        if (isEmailExist)
            throw new BadRequestException("Email Already Exist!");

        // Check if role is valid
        try {
            Role role = Role.valueOf(userSaveRequestDto.getRole().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Invalid Role!");
        }

        User user = modelMapper.map(userSaveRequestDto, User.class);

        String passwordHash = passwordEncoder.encode(userSaveRequestDto.getPassword());

        user.setPassword(passwordHash);
        user.setStatus(WellKnownStatus.ACTIVE.getValue());
        user.setIsVerified(false);

        User savedUser = userRepository.save(user);

        UserResponseDto userResponseDto = modelMapper.map(savedUser, UserResponseDto.class);

        return userResponseDto;
    }

    @Override
    public UserResponseDto getUserProfile(Long userId) {
        UserResponseDto userResponseDto = null;
        User user = userRepository.getReferenceById(userId);

        if (user == null || user.getStatus() == WellKnownStatus.DELETED.getValue())
            throw new NotFoundException("User not found!");
        else {
            userResponseDto = modelMapper.map(user, UserResponseDto.class);
        }
        return userResponseDto;
    }

    @Override
    public UserResponseDto updateUserProfile(Long userId, UserUpdateRequestDto userUpdateRequestDto) {
        UserResponseDto userResponseDto = null;
        User user = userRepository.getReferenceById(userId);

        if (user != null && user.getStatus() != WellKnownStatus.DELETED.getValue()) {
            user.setFirstName(userUpdateRequestDto.getFirstName());
            user.setLastName(userUpdateRequestDto.getLastName());
            user.setEmail(userUpdateRequestDto.getEmail());
            user.setPhoneNumber(userUpdateRequestDto.getPhoneNumber());
            user.setProfileImage(userUpdateRequestDto.getProfileImage());

            try{
                Role role = Role.valueOf(userUpdateRequestDto.getRole().toUpperCase());
                user.setRole(role);
            }catch (IllegalArgumentException e){
                throw new BadRequestException("Invalid Role!");
            }

            User savedUser = userRepository.save(user);

            userResponseDto = modelMapper.map(savedUser, UserResponseDto.class);

        } else {
            throw new NotFoundException("User not found!");
        }
        return userResponseDto;
    }

    @Override
    public void deleteUserProfile(Long userId) {
        User user = userRepository.getReferenceById(userId);

        if(user != null && user.getStatus() != WellKnownStatus.DELETED.getValue()){
            user.setStatus(WellKnownStatus.DELETED.getValue());

            userRepository.save(user);
        }else {
            throw new NotFoundException("User not found!");
        }
    }
}
