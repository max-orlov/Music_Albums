package com.max_orlov.music_albums_store.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Permission {
    USER_WRITE("user:write"),
    ADMIN_MODERATE("admin:addAlbum");

    private String permission;

}
