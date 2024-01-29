package com.example.demo.config;

import com.example.demo.user.CustomUserDetails;
import com.example.demo.user.UserService;
import com.example.demo.util.JwtTokenUtil;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.Tested;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

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
    void doFilterInternalValidToken() throws Exception {
        final String userId = "testUserId";
        final String token = "Bearer validToken";
        final CustomUserDetails userDetails = new CustomUserDetails(); // Properly initialize this object

        new Expectations() {{
            request.getHeader("Authorization");
            result = token;
            jwtTokenUtil.getUserIdFromToken(token.substring(7));
            result = userId;
            userService.getUserById(userId);
            result = userDetails;
            jwtTokenUtil.validateToken(token.substring(7), userDetails);
            result = true;
        }};

        jwtRequestFilter.doFilterInternal(request, response, filterChain);

        assertNotNull(SecurityContextHolder.getContext().getAuthentication(), "Authentication should not be null");
        assertTrue(SecurityContextHolder.getContext().getAuthentication() instanceof UsernamePasswordAuthenticationToken, "Authentication type should be UsernamePasswordAuthenticationToken");
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void doFilterInternalInvalidToken() throws Exception {
        final String token = "Bearer invalidToken";

        new Expectations() {{
            request.getHeader("Authorization");
            result = token;
            jwtTokenUtil.getUserIdFromToken(token.substring(7));
            result = null;
        }};

        jwtRequestFilter.doFilterInternal(request, response, filterChain);

        assertNull(SecurityContextHolder.getContext().getAuthentication(), "Authentication should be null for invalid token");
        verify(filterChain).doFilter(request, response);
    }
}
