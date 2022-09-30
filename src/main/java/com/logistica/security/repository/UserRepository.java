package com.logistica.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.logistica.security.model.User;


public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
