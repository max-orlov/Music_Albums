package com.max_orlov.music_albums_store.filter;

import org.apache.logging.log4j.ThreadContext;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class UserNameFilter implements Filter {
    private static final String THREAD_CONTEXT_KEY = "userName";

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        Cookie[] cookies = request.getCookies();
        String userName = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(THREAD_CONTEXT_KEY)) {
                    userName = cookie.getValue();
                }
            }
        }
        if (userName != null) {
            ThreadContext.put(THREAD_CONTEXT_KEY, userName);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

}
