package com.auth_app.authenticationApp.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.auth_app.authenticationApp.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository {

    // JDBC TEMPLATE
    private final JdbcTemplate jdbcTemplate;
    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Mapear ResultSet para objeto User
    private User mapRowToUser(ResultSet resultSet, int rowNum) throws SQLException {
        return new User (
                resultSet.getString("name"),
                resultSet.getInt("age"),
                resultSet.getString("email")
        );
    }


    // GET por ID
    public User findOne(String email) {
        String sql = "SELECT name, age, email FROM users WHERE email = ?";
        return jdbcTemplate.queryForObject(sql, this::mapRowToUser, email);
    }

    // GET All
    public List<User> findAll () {
        String sql = "SELECT name, age, email FROM users";
        return jdbcTemplate.query(sql, this::mapRowToUser);
    }

    // GET Email
    public boolean findEmail(String email) {
        String sql = "SELECT COUNT(1) FROM users WHERE email = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        System.out.println(count);
        return count == null || count > 0;
    }

    // POST
    public void save(User user) {
        String sql = "INSERT INTO users (name, age, email, password) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, user.getName(), user.getAge(), user.getEmail(), user.getPassword());
    }

    // PATCH User name e age
    public void updateNameAge(User user) {
        // Construir o Update de forma dinamica
        StringBuilder sql = new StringBuilder("UPDATE users SET ");
        List<Object> params = new ArrayList<>();

        if (user.getName() != null) {
            sql.append("name = ?, ");
            params.add(user.getName());
        }

        if (user.getAge() > 0) {
            sql.append("age = ?, ");
            params.add(user.getAge());
        }

        sql.delete(sql.length() - 2, sql.length());
        sql.append(" WHERE email = ?");
        params.add(user.getEmail());

        jdbcTemplate.update(sql.toString(), params.toArray());
    }

    // PATCH User email
    public void updateEmail(User user) {
        String sql = "UPDATE users SET email = ? WHERE email = ?";

        jdbcTemplate.update(sql, user.getEmail(), user.getEmail());
    }

    // PATCH User passwd
    public void updatePasswd(User user) {
        String sql = "UPDATE users SET password = ? WHERE email = ?";

        jdbcTemplate.update(sql, user.getPassword(), user.getEmail());
    }

    // DELETE User
    public void delete(String email) {
        String sql = "DELETE users WHERE email = ?";
         jdbcTemplate.update(sql, email);
    }

}
