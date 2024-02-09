package com.example.demo.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDao extends JpaRepository<CustomUserDetails, String> {

    @Query("SELECT u FROM CustomUserDetails u WHERE u.username = ?1")
    Optional<CustomUserDetails> findByUsername(final String username);
}
