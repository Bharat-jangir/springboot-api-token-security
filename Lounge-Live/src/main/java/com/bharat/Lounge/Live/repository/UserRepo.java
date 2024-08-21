package com.bharat.Lounge.Live.repository;

import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;

import com.bharat.Lounge.Live.entities.Role;
import com.bharat.Lounge.Live.entities.User;

public interface UserRepo extends JpaRepository<User,String> {

    Optional<User> findByEmail(String email);

    User findByRole(Role role);
}
