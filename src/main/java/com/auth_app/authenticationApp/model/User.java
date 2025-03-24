package com.auth_app.authenticationApp.model;

public class User {
    private String name;
    private int age;
    private String email;
    private String password;

    public User(String name, int age, String email, String password) {
        setName(name);
        setAge(age);
        setEmail(email);
        setPassword(password);
    }

    // Construtor sem a senha para GET user
    public User(String name, int age, String email) {
        setName(name);
        setAge(age);
        setEmail(email);
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
