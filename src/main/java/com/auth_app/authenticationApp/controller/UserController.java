package com.auth_app.authenticationApp.controller;

import com.auth_app.authenticationApp.exception.ValidationException;
import com.auth_app.authenticationApp.model.UserDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import com.auth_app.authenticationApp.model.User;
import com.auth_app.authenticationApp.service.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK) // HTTP 200
    public List<UserDTO> UserGetAll() {
        List<User> users = userService.getAllUsers();
        List<UserDTO> userDTOs = users.stream()
                .map(user -> new UserDTO(user.getName(), user.getAge(), user.getEmail()))
                .collect(Collectors.toList());
        return userDTOs;
    }

    @GetMapping("/{email}")
    @ResponseStatus(HttpStatus.OK) // HTTP 200
    public UserDTO UserGetEmail(@PathVariable("email") String email) {
        User user = userService.getUser(email);
        return new UserDTO(user.getName(), user.getAge(), user.getEmail());
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED) // HTTP 201
    public User UserPost(@Valid @RequestBody User data) {
        return userService.userDataCreate(data);
    }

    // Exception Validation
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(ValidationException ex) {
        // Variável resposta de erro
        Map<String, String> response = Map.of("error", ex.getMessage());
        return ResponseEntity.status(ex.getStatus()).body(response);
    }

    // Exception para erros de validação de parametros na classe User
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidateException(MethodArgumentNotValidException ex) {
        // Variável resposta dos erros de validação de parametros
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST); // HTTP 400
    }

}
