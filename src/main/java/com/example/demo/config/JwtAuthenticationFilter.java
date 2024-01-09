package com.example.demo.config;

import com.example.demo.user.CustomUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
  
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
    throws ServletException, IOException {
    
    String authorizationHeader = request.getHeader("Authorization");
    
    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
      String jwt = authorizationHeader.substring(7); // Remove "Bearer " prefix
      // Validate and extract user details from the JWT token
      UserDetails userDetails = extractUserDetailsFromJwt(jwt);
      
      if (userDetails != null) {
        UsernamePasswordAuthenticationToken authentication =
          new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext()
                             .setAuthentication(authentication);
      }
    }
    
    filterChain.doFilter(request, response);
  }
  
  // Implement JWT validation and user details extraction logic
  private UserDetails extractUserDetailsFromJwt(String jwt) {
    // Your implementation here
    // Validate the JWT and extract user details
    return new CustomUserDetails("P-0123456789", "username", ""); // Replace with actual user details
  }
}