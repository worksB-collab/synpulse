package com.example.demo.user;

import com.example.demo.account.AccountService;
import com.example.demo.util.JwtTokenUtil;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.example.demo.user.UserOm.newUser;
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
        final String username = "test";
        final CustomUserDetails userDetails = newUser(username, null);

        new Expectations() {{
            userDao.findByUsername(username);
            result = userDetails;
        }};

        final UserDetails result = userService.loadUserByUsername(username);

        assertEquals(userDetails, result);
    }

    @Test
    void loadUserByUsernameNotFound() {
        final String username = "test";

        new Expectations() {{
            userDao.findByUsername(username);
            result = Optional.empty();
        }};

        assertThrows(ResponseStatusException.class, () -> userService.loadUserByUsername(username));
    }

    @Test
    void loginSuccessful() {
        final String username = "username";
        final String password = "password";
        final CustomUserDetails userDetails = newUser(username, password);
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

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody() instanceof Map);
        assertEquals(token, ((Map) response.getBody()).get("token"));
        assertEquals(accountIds, ((Map) response.getBody()).get("accountList"));
    }

    @Test
    void loginFailed() {
        final String username = "username";
        final String password = "password";
        final CustomUserDetails userDetails = newUser();

        new Expectations() {{
            userDao.findByUsername(username);
            result = userDetails;
            passwordEncoder.matches(password, userDetails.getPassword());
            result = false;
        }};

        final ResponseEntity<?> response = userService.login(username, password);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Invalid credentials", response.getBody());
    }
}
