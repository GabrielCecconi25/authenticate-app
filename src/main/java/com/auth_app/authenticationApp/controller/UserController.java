package com.auth_app.authenticationApp.controller;

import org.hibernate.mapping.Any;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.auth_app.authenticationApp.model.User;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping
    public String UserGetAll() {
        return "Teste";
    }

    @GetMapping("/{id}")
    public String UserGetId(@PathVariable ("id") String id) {
        return id;
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public User UserPost(@RequestBody Map<String, String> body) {
        String name = body.get("name");
        String email = body.get("email");
        String password = body.get("password");
        Integer idade = Integer.parseInt(body.get("idade"));

        return new User(name, email, password, idade);
    }
}
