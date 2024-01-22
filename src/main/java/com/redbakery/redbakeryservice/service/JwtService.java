package com.redbakery.redbakeryservice.service;

import com.redbakery.redbakeryservice.model.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashMap;

public interface JwtService {
    String extractUserName(String token);

    String generateToken(UserDetails userDetails);

    boolean isTokenValid(String token, UserDetails userDetails);

    String generateRefreshT(HashMap<String, Object> extraClaims, User user);

    User getUserFromJWT(String token);
}
