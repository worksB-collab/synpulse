package com.example.demo.user;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
@Setter
public class CustomUserDetails implements UserDetails {
  
  private String userId;  // Unique identity key, e.g., P-0123456789
  private String username;
  private String password;  // You may not need this if using JWT
  
  public CustomUserDetails(String userId, String username, String password) {
    this.userId = userId;
    this.username = username;
    this.password = password;
  }
  
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    // You may implement roles and permissions here if needed
    return null;
  }
  
  @Override
  public String getPassword() {
    return password;
  }
  
  @Override
  public String getUsername() {
    return username;
  }
  
  @Override
  public boolean isAccountNonExpired() {
    return true;  // Modify as needed
  }
  
  @Override
  public boolean isAccountNonLocked() {
    return true;  // Modify as needed
  }
  
  @Override
  public boolean isCredentialsNonExpired() {
    return true;  // Modify as needed
  }
  
  @Override
  public boolean isEnabled() {
    return true;  // Modify as needed
  }
  
}
