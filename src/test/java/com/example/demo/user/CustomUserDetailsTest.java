package com.example.demo.user;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class CustomUserDetailsTest {

    @Test
    void userDetailsGetters() {
        final String userId = "testUserId";
        final String username = "testUsername";
        final String password = "testPassword";

        final CustomUserDetails userDetails = new CustomUserDetails(userId, username, password);

        assertEquals(userId, userDetails.getUserId(), "getUserId should return correct userId");
        assertEquals(username, userDetails.getUsername(), "getUsername should return correct username");
        assertEquals(password, userDetails.getPassword(), "getPassword should return correct password");
    }

    @Test
    void userDetailsAuthorities() {
        final CustomUserDetails userDetails = new CustomUserDetails();

        final Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

        assertNull(authorities, "getAuthorities should return null as per current implementation");
    }

    @Test
    void userDetailsBooleanProperties() {
        final CustomUserDetails userDetails = new CustomUserDetails();

        assertTrue(userDetails.isAccountNonExpired(), "isAccountNonExpired should return true");
        assertTrue(userDetails.isAccountNonLocked(), "isAccountNonLocked should return true");
        assertTrue(userDetails.isCredentialsNonExpired(), "isCredentialsNonExpired should return true");
        assertTrue(userDetails.isEnabled(), "isEnabled should return true");
    }
}
