package com.auth_app.authenticationApp.service;

import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;

import com.auth_app.authenticationApp.model.User;
import com.auth_app.authenticationApp.exception.ValidationException;
import com.auth_app.authenticationApp.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User userDataCreate(User user) {

        // Verifica duplicação de Email
        if (userRepository.findEmail(user.getEmail())) {
            throw new ValidationException("Email alredy in use", HttpStatus.CONFLICT); // HTTP 409
        }

        try {
            userRepository.save(user);
        } catch (Exception e) {
            throw new ValidationException("Internal Error try again later", HttpStatus.INTERNAL_SERVER_ERROR); // HTTP 500
        }

        return user;
    }

}
