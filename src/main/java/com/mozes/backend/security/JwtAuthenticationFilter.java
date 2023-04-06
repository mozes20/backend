package com.mozes.backend.security;

import com.mozes.backend.exceptions.JwtAuthenticationException;
import io.jsonwebtoken.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String token = httpServletRequest.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        } else {
            token = null;
        }
        if (token != null && !jwtTokenUtil.isTokenExpired(token)) {
            String username = jwtTokenUtil.getUsernameFromToken(token);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (jwtTokenUtil.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(auth);
            } else {
                throw new JwtAuthenticationException("JWT token is not valid");
            }
        }
        try {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } catch (java.io.IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        // Exclude authentication path from filtering
        return StringUtils.equals(request.getServletPath(), "/authenticate");
    }
}
