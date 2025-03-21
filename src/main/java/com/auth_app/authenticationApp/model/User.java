package com.auth_app.authenticationApp.model;

public class User {
    public String name;
    public Integer age;
    private String email;
    private String password;

    public User(String name, Integer age, String email, String password) {
        this.name = name;
        this.age = age;
        setEmail(email);
        setPassword(password);
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
