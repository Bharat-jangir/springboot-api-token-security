package com.bharat.Lounge.Live;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.bharat.Lounge.Live.entities.Role;
import com.bharat.Lounge.Live.entities.User;
import com.bharat.Lounge.Live.repository.UserRepo;

@SpringBootApplication
public class LoungeLiveApplication {

    @Autowired
    private UserRepo userRepo;
    public static void main(String[] args) {
        SpringApplication.run(LoungeLiveApplication.class, args);
        
    }

    public void run(String ...args){
        User adminAccount= userRepo.findByRole(Role.ADMIN);
        if(adminAccount==null){
            User user=new User();
            user.setId(UUID.randomUUID().toString());
            user.setFistName("admin");
            user.setEmail("admin@gmail.com");
            user.setSecondName("admin");
            user.setRole(Role.ADMIN);
            user.setPassword(new BCryptPasswordEncoder().encode("admin"));
            userRepo.save(user);
        }
    }

    
}
