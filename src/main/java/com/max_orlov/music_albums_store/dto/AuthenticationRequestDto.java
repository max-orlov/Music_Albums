package com.max_orlov.music_albums_store.dto;

import lombok.Data;

@Data
public class AuthenticationRequestDto {
    private String userName;
    private String password;
}
