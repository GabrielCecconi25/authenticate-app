package com.auth_app.authenticationApp.model;

public class User {
    public String name;
    private String email;
    private String password;
    public Integer idade;

    public User(String name, String email, String password, Integer idade) {
        this.name = name;
        setEmail(email);
        setPassword(password);
        this.idade = idade;
    }

    // Setter email
    public void setEmail(String email) {
        this.email = email;
    }
    // Getter email
    public String getEmail() {
        return this.email;
    }

    // Setter Password
    public void setPassword(String password) {
        this.password = password;
    }
    // Getter Password
    public String getPassword() {
        return this.password;
    }
}
