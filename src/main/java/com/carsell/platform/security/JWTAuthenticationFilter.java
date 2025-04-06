package com.carsell.platform.security;

import com.carsell.platform.exception.JwtAuthenticationProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authorization.AuthenticatedAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Collections;


@RequiredArgsConstructor
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    private final JWTUtil jwtUtil;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response)
            throws AuthenticationException {

        try {
            // Check if the request body is empty
            if (request.getContentLength() == 0) {
                String errMsg = "Login request body is empty";
                logger.error(errMsg);
                throw new JwtAuthenticationProcessingException(errMsg);
            }
            LoginRequest loginRequest = objectMapper.readValue(request.getInputStream(), LoginRequest.class);
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword(),
                            Collections.emptyList()
                    );
            Authentication manager = authenticationManager.authenticate(authToken);
            return manager;
        } catch (Exception e) {
            String errMsg = "Could not read login request: " + e.getMessage();
            logger.error(errMsg, e);
            throw new JwtAuthenticationProcessingException("Could not read login request: " + e.getMessage(), e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult)
            throws IOException, ServletException {
        String token = jwtUtil.generateJwtToken(authResult.getName());
        String fullToken = "Bearer " + token;
        response.addHeader("Authorization", fullToken);

        // Save token in session for stateful use.
        request.getSession(true).setAttribute("JWT_TOKEN", token);

        // Optionally write token in response body as JSON:
        response.setContentType("application/json");
        objectMapper.writeValue(response.getWriter(), Collections.singletonMap("token", token));
    }

}
