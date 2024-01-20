package com.example.demo.user;

import com.example.demo.JwtTokenUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static com.example.demo.Util.convertJsonToObject;

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
        return userDao.findByUsername(email).orElseThrow(() -> new IllegalArgumentException(USER_NOT_EXIST));
    }

    public CustomUserDetails getUserById(final String userId) {
        return userDao.findById(userId).orElseThrow(() -> new UsernameNotFoundException(USER_NOT_EXIST));
    }

    public CustomUserDetails getUserByUsername(final String username) {
        return userDao.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(USER_NOT_EXIST));
    }

    public void saveUser(final String userJson) {
        userDao.save(convertJsonToObject(userJson, CustomUserDetails.class));
    }

    public ResponseEntity<?> login(final String username, final String password) throws Exception {
        final CustomUserDetails userDetails = this.getUserByUsername(username);

        if (passwordEncoder.matches(password, userDetails.getPassword())) {
            final String token = jwtTokenUtil.generateToken(userDetails);
            return ResponseEntity.ok(token);
        } else {
            return ResponseEntity.badRequest().body("Invalid credentials");
        }
    }

}
