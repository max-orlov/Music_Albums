package com.max_orlov.music_albums_store.config;

import com.max_orlov.music_albums_store.security.CsrfTokenStorage;
import com.max_orlov.music_albums_store.security.LoadCsrfToken;
import com.max_orlov.music_albums_store.security.ValidateCsrfToken;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@AllArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private static final String EXCLUDED_URL = "/**";

    private CsrfTokenStorage csrfTokenStorage;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ValidateCsrfToken(csrfTokenStorage))
                .excludePathPatterns(EXCLUDED_URL);
        registry.addInterceptor(new LoadCsrfToken(csrfTokenStorage));
    }
}
