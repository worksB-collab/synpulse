package com.example.demo.config;

import com.example.demo.user.CustomUserDetails;
import com.example.demo.user.UserService;
import com.example.demo.util.JwtTokenUtil;
import mockit.*;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class JwtRequestFilterTest {

    @Tested
    private JwtRequestFilter jwtRequestFilter;

    @Injectable
    private UserService userService;

    @Injectable
    private JwtTokenUtil jwtTokenUtil;

    @Mocked
    private HttpServletRequest request;

    @Mocked
    private HttpServletResponse response;

    @Mocked
    private FilterChain filterChain;

    @Test
    void doFilterInternalWithValidToken() throws Exception {
        final String token = "Bearer validToken";
        final String userId = "userId";
        final CustomUserDetails user = new CustomUserDetails();

        new Expectations() {{
            request.getHeader("Authorization");
            result = token;

            jwtTokenUtil.getUserIdFromToken(token.substring(7));
            result = userId;

            userService.getUserById(userId);
            result = user;

            jwtTokenUtil.validateToken(token.substring(7), user);
            result = true;
        }};

        jwtRequestFilter.doFilterInternal(request, response, filterChain);

        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void doFilterInternalWithInvalidToken() throws Exception {
        final String token = "Bearer invalidToken";

        new Expectations() {{
            request.getHeader("Authorization");
            result = token;

            jwtTokenUtil.getUserIdFromToken(token.substring(7));
            result = null;
        }};

        jwtRequestFilter.doFilterInternal(request, response, filterChain);

        new Verifications() {{
            filterChain.doFilter(request, response);
        }};
    }

    @Test
    void doFilterInternalWithUserNotFound() throws Exception {
        final String token = "Bearer validToken";
        final String userId = "userId";

        new Expectations() {{
            request.getHeader("Authorization");
            result = token;

            jwtTokenUtil.getUserIdFromToken(token.substring(7));
            result = userId;

            userService.getUserById(userId);
            result = new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }};

        assertThrows(ResponseStatusException.class, () -> {
            jwtRequestFilter.doFilterInternal(request, response, filterChain);
        });

        new Verifications() {{
            filterChain.doFilter(request, response);
            times = 0;
        }};
    }
}
