package com.auth_app.authenticationApp.serivice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;

import com.auth_app.authenticationApp.model.User;
import com.auth_app.authenticationApp.exception.ValidationException;
import com.auth_app.authenticationApp.repository.UserRepository;

import java.util.*;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User userDataCreate(Map <String, String> data) {
        List<String> requiredFields = List.of("name", "age", "email", "password");
        List<String> missingFields = new ArrayList<String>();

        // Lógica para verificação de campos
        for (String field : requiredFields) {
            if (!data.containsKey(field) || data.get(field) == null || data.get(field).isEmpty()) {
                missingFields.add(field);
            }
        }
        if (!missingFields.isEmpty()) {
            throw new ValidationException("Missing required fields: " + missingFields, HttpStatus.BAD_REQUEST);
        }


        // Lógica para verificação de idade como Integer
        try {
            Integer age = Integer.parseInt(data.get("age"));
            if (age <= 0) {
                throw new ValidationException("Age must be greater than zero", HttpStatus.UNPROCESSABLE_ENTITY);
            }
        } catch (NumberFormatException e) {
            throw new ValidationException("Age must be a valid number", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        User user = new User(data.get("name"), Integer.parseInt(data.get("age")), data.get("email"), data.get("password"));
        try {
            userRepository.save(user);
        } catch (Exception e) {
            throw new ValidationException("Erro interno tente novamente", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return user;
    }

}
