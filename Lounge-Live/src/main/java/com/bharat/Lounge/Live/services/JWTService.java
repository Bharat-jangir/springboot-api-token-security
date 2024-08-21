package com.bharat.Lounge.Live.services;

import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;


public interface JWTService {

    String extractUserName(String token);

    String generateToken(UserDetails userDetails);

    boolean isTokenValid(String token,UserDetails details);

    String generateRefreshToken(Map<String,Object> hashMap,UserDetails userDetails);
}
