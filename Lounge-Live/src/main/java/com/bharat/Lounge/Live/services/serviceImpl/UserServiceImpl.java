package com.bharat.Lounge.Live.services.serviceImpl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bharat.Lounge.Live.repository.UserRepo;
import com.bharat.Lounge.Live.services.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepo userRepo;

    @Override
    public UserDetailsService userDetailsService(){
        return new UserDetailsService() {
            
            @Override
            public UserDetails loadUserByUsername(String username){
                return userRepo.findByEmail(username)
                .orElseThrow(()->new UsernameNotFoundException("user not fund"));
    
            }
        };

    }

}
