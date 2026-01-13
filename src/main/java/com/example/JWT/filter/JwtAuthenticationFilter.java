package com.example.JWT.filter;

import com.example.JWT.model.Role;
import com.example.JWT.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, 
                                  FilterChain filterChain) throws ServletException, IOException {
        
        logger.debug("Processing request: {} {}", request.getMethod(), request.getRequestURI());
        
        final String authorizationHeader = request.getHeader("Authorization");
        
        String username = null;
        String jwt = null;
        String userId = null;
        Role role = null;

        // Try to get JWT from Authorization header first
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            logger.debug("Found JWT in Authorization header");
        } else {
            // If no Authorization header, try to get JWT from cookie
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("jwt".equals(cookie.getName())) {
                        jwt = cookie.getValue();
                        logger.debug("Found JWT in cookie");
                        break;
                    }
                }
            }
        }

        if (jwt != null) {
            try {
                username = jwtUtil.extractUsername(jwt);
                userId = jwtUtil.extractUserId(jwt);
                role = jwtUtil.extractRole(jwt);
                logger.debug("Extracted from JWT - username: {}, userId: {}, role: {}", username, userId, role);
            } catch (Exception e) {
                logger.error("JWT token validation failed", e);
            }
        } else {
            logger.debug("No JWT token found in request");
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            if (jwtUtil.validateToken(jwt, username)) {
                UsernamePasswordAuthenticationToken authToken = 
                    new UsernamePasswordAuthenticationToken(
                        username, 
                        null, 
                        List.of(new SimpleGrantedAuthority("ROLE_" + role.name()))
                    );
                
                request.setAttribute("userId", userId);
                request.setAttribute("userRole", role);
                
                logger.debug("Setting authentication for user: {} with role: ROLE_{}", username, role.name());
                SecurityContextHolder.getContext().setAuthentication(authToken);
            } else {
                logger.debug("Token validation failed for user: {}", username);
            }
        }
        
        filterChain.doFilter(request, response);
    }
}