package com.max_orlov.music_albums_store.security;

import lombok.Data;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@Data
public class CsrfTokenStorage {

    private final List<String> csrfTokens =
            Collections.synchronizedList(new ArrayList<>());

    public void addTokenToStorage(String token) {
        csrfTokens.add(token);
    }

    public String getNewToken() {
        String token =
                RandomStringUtils.random(20, 0, 0, true, true, null,
                        new SecureRandom());
        addTokenToStorage(token);
        return token;
    }

    public void removeToken(String token) {
        csrfTokens.remove(token);
    }

}
