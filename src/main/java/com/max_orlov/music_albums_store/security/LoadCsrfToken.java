package com.max_orlov.music_albums_store.security;

import lombok.AllArgsConstructor;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@AllArgsConstructor
public class LoadCsrfToken extends HandlerInterceptorAdapter{

    private static final String CSRF_TOKEN_HEADER = "X-CSRF-Token";

    private CsrfTokenStorage csrfTokenStorage;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        String newToken = csrfTokenStorage.getNewToken();
        response.setHeader(CSRF_TOKEN_HEADER, newToken);
        return true;
    }

}
