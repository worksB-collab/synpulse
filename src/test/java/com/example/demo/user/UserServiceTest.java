package com.example.demo.user;

import com.example.demo.account.AccountService;
import com.example.demo.util.JwtTokenUtil;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    @Tested
    private UserService userService;

    @Injectable
    private UserDao userDao;

    @Injectable
    private AccountService accountService;

    @Injectable
    private BCryptPasswordEncoder passwordEncoder;

    @Injectable
    private JwtTokenUtil jwtTokenUtil;

    @Test
    void loadUserByUsernameSuccess() {
        final String email = "test@example.com";
        final CustomUserDetails userDetails = new CustomUserDetails(); // Initialize as needed

        new Expectations() {{
            userDao.findByUsername(email);
            result = userDetails;
        }};

        final UserDetails result = userService.loadUserByUsername(email);

        assertEquals(userDetails, result, "Should return the correct UserDetails");
    }

    @Test
    void loadUserByUsernameNotFound() {
        final String email = "test@example.com";

        new Expectations() {{
            userDao.findByUsername(email);
            result = null;
        }};

        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername(email), "Should throw UsernameNotFoundException");
    }

    @Test
    void loginSuccessful() {
        final String username = "testUser";
        final String password = "password";
        final CustomUserDetails userDetails = new CustomUserDetails(); // Initialize as needed
        userDetails.setPassword(password);
        final String token = "jwtToken";
        final List<Long> accountIds = List.of(1L, 2L);

        new Expectations() {{
            userDao.findByUsername(username);
            result = userDetails;
            passwordEncoder.matches(password, userDetails.getPassword());
            result = true;
            jwtTokenUtil.generateToken(userDetails);
            result = token;
            accountService.getAccountIds(anyString);
            result = accountIds;
        }};

        final ResponseEntity<?> response = userService.login(username, password);

        assertEquals(200, response.getStatusCodeValue(), "Response status should be 200 OK");
        assertTrue(response.getBody() instanceof Map, "Response body should be a map");
        assertEquals(token, ((Map) response.getBody()).get("token"), "Token should be correct");
        assertEquals(accountIds, ((Map) response.getBody()).get("accountList"), "Account list should be correct");
    }

    @Test
    void loginFailed() {
        final String username = "testUser";
        final String password = "password";
        final CustomUserDetails userDetails = new CustomUserDetails(); // Initialize as needed
        userDetails.setPassword("differentPassword");

        new Expectations() {{
            userDao.findByUsername(username);
            result = userDetails;
            passwordEncoder.matches(password, userDetails.getPassword());
            result = false;
        }};

        final ResponseEntity<?> response = userService.login(username, password);

        assertEquals(400, response.getStatusCodeValue(), "Response status should be 400 Bad Request");
        assertEquals("Invalid credentials", response.getBody(), "Response body should indicate invalid credentials");
    }
}
