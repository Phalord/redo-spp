package com.spp.model.dataaccess.dao;

import com.spp.model.dataaccess.idao.IUserDAO;
import com.spp.model.domain.Coordinator;
import com.spp.model.domain.User;
import com.spp.utils.MySQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDAO implements IUserDAO<User> {
    private final MySQLConnection mySQLConnection;

    public UserDAO() {
        mySQLConnection = new MySQLConnection();
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String query = "SELECT Username, userType";
        try (Connection connection = mySQLConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while(resultSet.next()) {
                User user = new Coordinator();
                user.setUsername(resultSet.getString("Username"));
                user.setUserType(resultSet.getString("userType"));
                users.add(user);
            }
        } catch (SQLException sqlException) {
            Logger.getLogger(UserDAO.class.getName())
                    .log(Level.SEVERE, sqlException.getMessage(), sqlException);
            return null;
        }
        return users;
    }

    @Override
    public User getUserByUsername(String studentEnrollment) {
        User user = null;
        String query = "SELECT * FROM User WHERE Username = ?";
        try (Connection connection = mySQLConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, studentEnrollment);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    user = new Coordinator();
                    user.setUsername(resultSet.getString("Username"));
                    user.setUserType(resultSet.getString("userType"));
                    user.setPassword(resultSet.getString("password"));
                    user.setActive(resultSet.getBoolean("status"));
                }
            }
        } catch (SQLException sqlException) {
            Logger.getLogger(UserDAO.class.getName())
                    .log(Level.SEVERE, sqlException.getMessage(), sqlException);
        }
        return user;
    }

    @Override
    public boolean addUser(User user) {
        return false;
    }

    @Override
    public boolean deleteUser(User user) {
        return false;
    }
}
