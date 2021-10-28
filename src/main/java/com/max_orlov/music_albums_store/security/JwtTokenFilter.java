package com.max_orlov.music_albums_store.security;

import com.max_orlov.music_albums_store.exception.JwtAuthenticationException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@AllArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private static final String JWT_COOKIE_NAME = "jwtToken";
    private static final String JWT_AUTHENTICATION_EXCEPTION_MESSAGE = "JWT token is expired or invalid";

    private JwtTokenUtil jwtTokenUtil;

    @Override
    public void doFilterInternal(HttpServletRequest request,
                                 HttpServletResponse response,
                                 FilterChain filterChain) throws IOException, ServletException {
        Cookie[] cookies = request.getCookies();
        String token = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(JWT_COOKIE_NAME)) {
                    token = cookie.getValue();
                }
            }
        }

        try {
            if (token != null && jwtTokenUtil.validateToken(token)) {
                Authentication authentication =
                        jwtTokenUtil.getAuthentication(token);
                if (authentication != null) {
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
            filterChain.doFilter(request, response);
        } catch (JwtAuthenticationException e) {
            SecurityContextHolder.clearContext();
            logger.error(e.getMessage());
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            throw new JwtAuthenticationException(JWT_AUTHENTICATION_EXCEPTION_MESSAGE, HttpStatus.UNAUTHORIZED);
        }
    }

}
