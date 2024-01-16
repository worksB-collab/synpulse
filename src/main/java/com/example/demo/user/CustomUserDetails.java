package com.example.demo.user;

import com.example.demo.transaction.Transaction;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Entity
@EqualsAndHashCode
@NoArgsConstructor
public class CustomUserDetails implements UserDetails {

  @Id
  @Setter(AccessLevel.NONE)
  @SequenceGenerator(
          name = "user_id_sequence",
          sequenceName = "user_id_sequence",
          allocationSize = 1
  )
  @GeneratedValue(
          strategy = GenerationType.SEQUENCE,
          generator = "user_id_sequence"
  )
  private String userId;  // Unique identity key, e.g., P-0123456789
  private String username;
  private String password;  // You may not need this if using JWT

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Transaction> transactionList;

  public CustomUserDetails(final String username, final String password) {
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
    return true;
  }
  
  @Override
  public boolean isAccountNonLocked() {
    return true;
  }
  
  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }
  
  @Override
  public boolean isEnabled() {
    return true;
  }
  
}
