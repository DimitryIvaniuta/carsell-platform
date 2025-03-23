package com.carsell.platform.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class JWTAuthorizationFilterTest {

    @Autowired
    private JWTUtil jwtUtil;

    // Our filter under test
    private JWTAuthorizationFilter jwtAuthorizationFilter;

    // A mock FilterChain to verify that the request passes along.
    private FilterChain filterChain;

    @BeforeEach
    public void setUp() {
        // Create the filter instance using the autowired jwtUtil.
        jwtAuthorizationFilter = new JWTAuthorizationFilter(jwtUtil);
        // Create a mock FilterChain.
        filterChain = mock(FilterChain.class);
        // Clear the SecurityContextHolder before each test.
        SecurityContextHolder.clearContext();
    }

    @AfterEach
    public void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void testDoFilterInternal_withValidToken() throws ServletException, IOException {
        // Generate a valid token for a test user "testUser".
        String token = jwtUtil.generateJwtToken("testUser");
        String authHeader = "Bearer " + token;

        // Create a mock HTTP request with the Authorization header.
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader(HttpHeaders.AUTHORIZATION, authHeader);
        MockHttpServletResponse response = new MockHttpServletResponse();

        // Invoke the filter.
        jwtAuthorizationFilter.doFilterInternal(request, response, filterChain);

        // Verify that the filter chain proceeded.
        verify(filterChain, times(1)).doFilter(request, response);

        // Check that the SecurityContextHolder is populated with an Authentication.
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        assertNotNull(auth, "SecurityContext should contain an Authentication object for a valid token");
        assertEquals("testUser", auth.getName(), "The authenticated username should be 'testUser'");
    }

    @Test
    void testDoFilterInternal_withInvalidToken() throws ServletException, IOException {
        // Create a request with an invalid token.
        String invalidToken = "Bearer invalid.token.value";
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader(HttpHeaders.AUTHORIZATION, invalidToken);
        MockHttpServletResponse response = new MockHttpServletResponse();

        jwtAuthorizationFilter.doFilterInternal(request, response, filterChain);

        // Verify that the filter chain proceeds.
        verify(filterChain, times(1)).doFilter(request, response);

        // SecurityContext should not have been populated.
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        assertNull(auth, "SecurityContext should be empty for an invalid token");
    }

    @Test
    void testDoFilterInternal_withoutAuthHeader() throws ServletException, IOException {
        // Create a request with no Authorization header.
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        jwtAuthorizationFilter.doFilterInternal(request, response, filterChain);

        // Verify that the filter chain proceeds.
        verify(filterChain, times(1)).doFilter(request, response);

        // SecurityContext should remain empty.
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        assertNull(auth, "SecurityContext should remain empty when no Authorization header is present");
    }

}
