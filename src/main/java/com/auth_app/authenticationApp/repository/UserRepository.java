package com.auth_app.authenticationApp.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.auth_app.authenticationApp.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
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

    public void save(User user) {
        String sql = "INSERT INTO users (name, age, email, password) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, user.getName(), user.getAge(), user.getEmail(), user.getPassword());
    }

    public User findOne(String email) {
        String sql = "SELECT name, age, email FROM users WHERE email = ?";
        return jdbcTemplate.queryForObject(sql, this::mapRowToUser, email);
    }

    public List<User> findAll () {
        String sql = "SELECT name, age, email FROM users";
        return jdbcTemplate.query(sql, this::mapRowToUser);
    }
}
