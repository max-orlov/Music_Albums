package com.max_orlov.music_albums_store.service;

import com.max_orlov.music_albums_store.model.User;

public interface UserService {

    User findByUsername(String userName);

}
