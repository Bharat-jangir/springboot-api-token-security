package com.bharat.Lounge.Live.services;

import com.bharat.Lounge.Live.dto.JwtAuthenticationResponse;
import com.bharat.Lounge.Live.dto.RefreshTokenRequest;
import com.bharat.Lounge.Live.dto.SignInRequest;
import com.bharat.Lounge.Live.dto.SignUpRequest;
import com.bharat.Lounge.Live.entities.User;

public interface AuthenticationService {
    User signup(SignUpRequest signUpRequest);
    JwtAuthenticationResponse signIn(SignInRequest signInRequest);
    JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
