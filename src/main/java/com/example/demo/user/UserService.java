package com.example.demo.user;

import com.example.demo.JwtTokenUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private static final String USER_NOT_EXIST = "user not exist";
    @Autowired
    private final UserDao userDao;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        return userDao.findByUsername(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, USER_NOT_EXIST));
    }

    public CustomUserDetails getUserById(final String userId) {
        return userDao.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, USER_NOT_EXIST));
    }

    public CustomUserDetails getUserByUsername(final String username) {
        return userDao.findByUsername(username).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, USER_NOT_EXIST));
    }

    public ResponseEntity<?> login(final String username, final String password) {
        final CustomUserDetails userDetails = this.getUserByUsername(username);

        if (passwordEncoder.matches(password, userDetails.getPassword())) {
            final String token = jwtTokenUtil.generateToken(userDetails);
            return ResponseEntity.ok(token);
        } else {
            return ResponseEntity.badRequest().body("Invalid credentials");
        }
    }

}
