package edu.ClassDesign.dao;

import edu.ClassDesign.global.global;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    public void addUser(User user) throws SQLException {
        String query = "INSERT INTO users (username, password, score) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setInt(3, user.getScore());
            statement.executeUpdate();
        }
    }

    public User getUser(String username) throws SQLException {
        String query = "SELECT * FROM users WHERE username = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    User user = new User();
                    user.setId(resultSet.getInt("id"));
                    user.setUsername(resultSet.getString("username"));
                    user.setPassword(resultSet.getString("password"));
                    user.setScore(resultSet.getInt("score"));
                    return user;
                }
            }
        }
        return null;
    }

    public List<User> getTopUsers() throws SQLException {
        String query = "SELECT * FROM users ORDER BY score DESC LIMIT 100";
        List<User> users = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setUsername(resultSet.getString("username"));
                user.setScore(resultSet.getInt("score"));
                users.add(user);
            }
        }
        return users;
    }

    public boolean UpdateScore() throws SQLException {
        String str = "update users set score= ? where username= ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(str)) {
            statement.setInt(1, global.user.getScore());
            statement.setString(2, global.user.getUsername());
            int res = statement.executeUpdate();
            if (res > 0) {
                System.out.println("Ìí¼Ó³É¹¦");
                return true;
            }
        }
        return false;
    }
}
