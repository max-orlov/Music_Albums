package com.max_orlov.music_albums_store.service;

import com.max_orlov.music_albums_store.model.User;
import com.max_orlov.music_albums_store.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Override
    public User findByUsername(String userName) {
        User user = null;
        Optional<User> byUsername =
                userRepository.findByUserName(userName);
        if (byUsername.isPresent()) {
            user = byUsername.get();
        }
        return user;
    }

}
