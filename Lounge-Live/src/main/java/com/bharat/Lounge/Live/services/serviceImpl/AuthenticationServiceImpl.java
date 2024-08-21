package com.bharat.Lounge.Live.services.serviceImpl;

import java.util.HashMap;
import java.util.UUID;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bharat.Lounge.Live.dto.JwtAuthenticationResponse;
import com.bharat.Lounge.Live.dto.RefreshTokenRequest;
import com.bharat.Lounge.Live.dto.SignInRequest;
import com.bharat.Lounge.Live.dto.SignUpRequest;
import com.bharat.Lounge.Live.entities.Role;
import com.bharat.Lounge.Live.entities.User;
import com.bharat.Lounge.Live.repository.UserRepo;
import com.bharat.Lounge.Live.services.AuthenticationService;
import com.bharat.Lounge.Live.services.JWTService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService{

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private  final AuthenticationManager authenticationManager;
    private final JWTService jwtService;


    public User signup(SignUpRequest signUpRequest){

        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setFistName(signUpRequest.getFirstName());
        user.setEmail(signUpRequest.getEmail());
        user.setSecondName(signUpRequest.getLastName());
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));

        return userRepo.save(user);


    }

    public JwtAuthenticationResponse signIn(SignInRequest signInRequest){

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getEmail(), signInRequest.getPassword()));

        var user=userRepo.findByEmail(signInRequest.getEmail()).orElseThrow(()->new IllegalArgumentException("Invalid username or password"));
        var jwt =jwtService.generateToken(user);
        var refreshToken =jwtService.generateRefreshToken(new HashMap<>(), user);
        JwtAuthenticationResponse jwtAuthenticationResponse= new JwtAuthenticationResponse();
        jwtAuthenticationResponse.setToken(jwt);
        jwtAuthenticationResponse.setRefreshToken(refreshToken);
        return jwtAuthenticationResponse;
    }


    public JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest){
        String userEmail=jwtService.extractUserName(refreshTokenRequest.getToken());
        User user=userRepo.findByEmail(userEmail).orElseThrow();
        if(jwtService.isTokenValid(refreshTokenRequest.getToken(), user)){
            var jwt =jwtService.generateToken(user);
            JwtAuthenticationResponse jwtAuthenticationResponse= new JwtAuthenticationResponse();
            jwtAuthenticationResponse.setToken(jwt);
            jwtAuthenticationResponse.setRefreshToken(refreshTokenRequest.getToken());
            return jwtAuthenticationResponse;
        }
        return null;
    }
}
