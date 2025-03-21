package com.auth_app.authenticationApp.controller;

import com.auth_app.authenticationApp.exception.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.auth_app.authenticationApp.model.User;
import com.auth_app.authenticationApp.serivice.UserService;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public String UserGetAll() {
        return "Teste";
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String UserGetId(@PathVariable ("id") String id) {
        return id;
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public User UserPost(@RequestBody Map<String, String> data) {
        return userService.userDataCreate(data);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(ValidationException ex) {
        // Variável resposta de erro
        Map<String, String> response = Map.of("error", ex.getMessage());

        return ResponseEntity.status(ex.getStatus()).body(response);
    }

}
