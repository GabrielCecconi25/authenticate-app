package com.auth_app.authenticationApp.service;

import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;

import com.auth_app.authenticationApp.model.User;
import com.auth_app.authenticationApp.exception.ValidationException;
import com.auth_app.authenticationApp.repository.UserRepository;

import java.util.List;
import java.util.Map;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers () {
        return userRepository.findAll();
    }

    public User getUser (String email) {
        return userRepository.findOne(email);
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

    public User updateUserNameAge(Map<String, String> data, String email) {

        User user = new User(data.get("name"), Integer.parseInt(data.get("age")), email);

        if (!userRepository.findEmail(user.getEmail())) {
            throw new ValidationException("Email is not valid", HttpStatus.NOT_FOUND); // HTTP 404
        }

        try {
            userRepository.updateNameAge(user);
        } catch (Exception e) {
            throw new ValidationException("Internal Error try again later", HttpStatus.INTERNAL_SERVER_ERROR); // HTTP 500
        }

        return user;
    }

    public Map <String, String> updateEmail(Map<String, String> data, String email) {
        User user = new User(data.get("email"));

        if (!userRepository.findEmail(email)) {
            throw new ValidationException("Email is not valid", HttpStatus.NOT_FOUND); // HTTP 404
        }

        if (userRepository.findEmail(user.getEmail())) {
            throw new ValidationException("Email alredy in use", HttpStatus.CONFLICT); // HTTP 409
        }

        try {
            userRepository.updateEmail(user, email);
        } catch (Exception e) {
            throw new ValidationException("Internal Error try again later", HttpStatus.INTERNAL_SERVER_ERROR); // HTTP 500
        }

        return data;
    }

    public Map <String, String> updatePasswd(Map<String, String> data, String email) {

        @Valid User user = new User(email, data.get("password"));

        if (!userRepository.findEmail(user.getEmail())) {
            throw new ValidationException("Email is not valid", HttpStatus.NOT_FOUND); // HTTP 404
        }

        try {
            userRepository.updatePasswd(user);
        } catch (Exception e) {
            throw new ValidationException("Internal Error try again later", HttpStatus.INTERNAL_SERVER_ERROR); // HTTP 500
        }

        return data;
    }

}
