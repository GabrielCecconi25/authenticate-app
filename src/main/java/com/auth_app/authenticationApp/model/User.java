package com.auth_app.authenticationApp.model;

import jakarta.validation.constraints.*;

public class User {
    @NotNull(message = "Name cannot be null")
    private String name;

    @Min(value = 1, message="Age must be greater than zero")
    private int age;

    @Email(message = "Email format Invalid")
    @NotNull(message = "Email cannot be null")
    private String email;

    @Size(min = 8, message = "Password must be at least 8 characters")
    @NotNull(message = "Password cannot be null")
    private String password;

    // Constructor para desserialização
    public User() {}

    // Constructor email
    public User(String email) {
        setEmail(email);
    }

    // Contructor senha
    public User(String email, String password) {
        setEmail(email);
        setPassword(password);
    }

    // Construtor sem a senha
    public User(String name, int age, String email) {
        setName(name);
        setAge(age);
        setEmail(email);
    }

    // Constructor padrão
    public User(String name, int age, String email, String password) {
        setName(name);
        setAge(age);
        setEmail(email);
        setPassword(password);
    }

    // Getter e Setter
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return this.name;
    }

    public void setAge(int age) {
        this.age = age;
    }
    public int getAge() {
        return age;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getEmail() {
        return this.email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getPassword() {
        return this.password;
    }
}
