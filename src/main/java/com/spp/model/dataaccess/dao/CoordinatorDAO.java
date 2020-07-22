package com.spp.model.dataaccess.dao;

import com.spp.model.dataaccess.idao.ICoordinatorDAO;
import com.spp.model.domain.Coordinator;
import com.spp.utils.MySQLConnection;
import org.mindrot.jbcrypt.BCrypt;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CoordinatorDAO implements ICoordinatorDAO {
    private final MySQLConnection mySQLConnection = new MySQLConnection();

    @Override
    public List<Coordinator> getAllUsers() {
        List<Coordinator> coordinators = new ArrayList<>();
        String query = "SELECT Username, name, surname FROM User WHERE userType = 'Coordinator'";
        try (Connection connection = mySQLConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Coordinator coordinator = new Coordinator();
                coordinator.setUsername(resultSet.getString("Username"));
                coordinator.setName(resultSet.getString("name"));
                coordinator.setSurnames(resultSet.getString("surname"));
                coordinators.add(coordinator);
            }
        } catch (SQLException sqlException) {
            Logger.getLogger(CoordinatorDAO.class.getName())
                    .log(Level.SEVERE, sqlException.getMessage(), sqlException);
            return null;
        }
        return coordinators;
    }

    @Override
    public Coordinator getUserByUsername(String username) {
        Coordinator coordinator = null;
        String query = "SELECT * FROM Coordinator INNER JOIN User ON Coordinator.Username = User.Username AND Coordinator.Username = ?";
        try (Connection connection = mySQLConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    coordinator = new Coordinator();
                    coordinator.setUsername(resultSet.getString("Username"));
                    coordinator.setName(resultSet.getString("name"));
                    coordinator.setSurnames(resultSet.getString("surname"));
                    coordinator.setActive(resultSet.getBoolean("status"));
                    coordinator.setUserType(resultSet.getString("userType"));
                }
            }
        } catch (SQLException sqlException) {
            Logger.getLogger(CoordinatorDAO.class.getName())
                    .log(Level.SEVERE, sqlException.getMessage(), sqlException);
        }
        return coordinator;
    }

    @Override
    public boolean addUser(Coordinator coordinator) {
        if (insertIntoUserTable(coordinator)) {
            return insertIntoCoordinator(coordinator);
        } else {
            return false;
        }
    }

    @Override
    public boolean deleteUser(Coordinator coordinator) {
        return false;
    }

    private boolean insertIntoUserTable(Coordinator coordinator) {
        boolean result = false;
        String query = "INSERT INTO User(Username,password,name,surname,userType,status) VALUES (?,?,?,?,?,?)";
        String passwordAux = BCrypt.hashpw(coordinator.getPassword(), BCrypt.gensalt(10));
        try (Connection connection = mySQLConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, coordinator.getUsername());
            preparedStatement.setString(2, passwordAux);
            preparedStatement.setString(3, coordinator.getName());
            preparedStatement.setString(4, coordinator.getSurnames());
            preparedStatement.setString(5, coordinator.getUserType());
            preparedStatement.setBoolean(6, coordinator.isActive());
            int numberRowsAffected = preparedStatement.executeUpdate();
            result = (numberRowsAffected > 0);
        } catch (SQLException sqlException) {
            Logger.getLogger(CoordinatorDAO.class.getName())
                    .log(Level.SEVERE, sqlException.getMessage(), sqlException);
        }
        return result;
    }

    private boolean insertIntoCoordinator(Coordinator coordinator) {
         boolean result = false;
        String query = "INSERT INTO Coordinator(Username) VALUES (?)";
        try (Connection connection = mySQLConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, coordinator.getUsername());
            int numberRowsAffected = preparedStatement.executeUpdate();
            result = (numberRowsAffected > 0);
        } catch (SQLException sqlException) {
            Logger.getLogger(CoordinatorDAO.class.getName())
                    .log(Level.SEVERE, sqlException.getMessage());
        }
        return result;
    }
    
    @Override
    public boolean existUser(String studentEnrollment) {
        boolean result = false;
        String query = "SELECT Username FROM User WHERE Username = ?";
        try (Connection connection = mySQLConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, studentEnrollment);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                result = resultSet.next();
            }
        } catch (SQLException sqlException) {
            Logger.getLogger(PractitionerDAO.class.getName())
                    .log(Level.SEVERE, sqlException.getMessage(), sqlException);
        }
        return result;
    }
}
