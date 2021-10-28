package com.max_orlov.music_albums_store.controller;

import com.max_orlov.music_albums_store.dto.AuthenticationRequestDto;
import com.max_orlov.music_albums_store.model.User;
import com.max_orlov.music_albums_store.repository.UserRepository;
import com.max_orlov.music_albums_store.security.JwtTokenUtil;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthenticationRestController {
    private static final Logger logger = LogManager.getLogger();

    private static final String THREAD_CONTEXT_KEY = "userName";
    private static final String JWT_HEADER_NAME = "jwtToken";
    private static final String USER_NOT_FOUND_EXCEPTION_MESSAGE = "User doesn't exists";
    private static final String AUTHENTICATION_EXCEPTION_MESSAGE = "Invalid userName/password combination";

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping(value = "/login")
    public ResponseEntity<?> authenticate(AuthenticationRequestDto request,
                                          HttpServletResponse response) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUserName(), request.getPassword()));
            User user = userRepository.findByUserName(
                    request.getUserName())
                    .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND_EXCEPTION_MESSAGE));
            ThreadContext.put(THREAD_CONTEXT_KEY, user.getUserName());
            logger.info("User logged in");
            String token = jwtTokenUtil.createToken(request.getUserName(),
                    user.getRole().name());
            response.setHeader(JWT_HEADER_NAME, token);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (AuthenticationException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(AUTHENTICATION_EXCEPTION_MESSAGE,
                    HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request,
                       HttpServletResponse response) {
        SecurityContextLogoutHandler securityContextLogoutHandler =
                new SecurityContextLogoutHandler();
        logger.info("User logged out");
        securityContextLogoutHandler.logout(request, response, null);
    }

}
