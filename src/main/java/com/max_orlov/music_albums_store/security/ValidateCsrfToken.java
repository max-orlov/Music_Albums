package com.max_orlov.music_albums_store.security;

import lombok.AllArgsConstructor;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@AllArgsConstructor
public class ValidateCsrfToken extends HandlerInterceptorAdapter {

    private static final String CSRF_MESSAGE = "CSRF attack detected";
    private static final String CSRF_TOKEN_HEADER = "X-CSRF-Token";

    private CsrfTokenStorage csrfTokenStorage;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        String token = request.getHeader(CSRF_TOKEN_HEADER);
        if (token != null && csrfTokenStorage.getCsrfTokens().contains(token)) {
            csrfTokenStorage.removeToken(token);
        } else {
            throw new ServletException(CSRF_MESSAGE);
        }
        return true;
    }

}
