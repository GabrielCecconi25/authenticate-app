package com.auth_app.authenticationApp.controller;

import com.auth_app.authenticationApp.exception.ValidationException;
import com.auth_app.authenticationApp.model.UserDTO;
import jakarta.validation.Valid;
import org.hibernate.validator.constraints.ParameterScriptAssert;
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


    @PutMapping("/{email}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Map<String, String>> UserPut(@Valid @RequestBody User data, @PathVariable("email") String email) {
        userService.UpdateUser(data, email);

        Map<String, String> response = new HashMap<>();
        response.put("mensagem", "User updated successfully");

        return ResponseEntity.ok(response);
    }


    // Updates
    @PatchMapping("/{email}")
    @ResponseStatus(HttpStatus.OK) // HTTP 200
    public UserDTO UserPatchNameAge(@Valid @RequestBody Map<String, String> data, @PathVariable("email") String email) {
        User user = userService.updateUserNameAge(data, email);

        return new UserDTO(user.getName(), user.getAge(), user.getEmail());
    }

    @PatchMapping("/update/email/{email}")
    @ResponseStatus(HttpStatus.OK) // HTTP 200
    public Map<String, String> UserPatchEmail(@Valid @RequestBody Map<String, String> data, @PathVariable("email") String email) {
        return userService.updateEmail(data, email);
    }

    @PatchMapping("/update/passwd/{email}")
    @ResponseStatus(HttpStatus.OK) // HTTP 200
    public Map<String, String> UserPatchPasswd(@Valid @RequestBody Map<String, String> data, @PathVariable("email") String email) {
        return userService.updatePasswd(data, email);
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
