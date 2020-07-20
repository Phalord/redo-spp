package com.spp.model.dataaccess.dao;

import com.spp.model.dataaccess.idao.IPractitionerDAO;
import com.spp.model.domain.Practitioner;
import com.spp.utils.MySQLConnection;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;

public class PractitionerDAO implements IPractitionerDAO {
    private final MySQLConnection mySQLConnection = new MySQLConnection();

    @Override
    public List<Practitioner> getAllUsers() {
        List<Practitioner> practitioners = new ArrayList<>();
        String query = "SELECT Username, name, surname FROM User WHERE userType = 'Practitioner'";
        try (Connection connection = mySQLConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Practitioner practitioner = new Practitioner();
                practitioner.setUsername(resultSet.getString("Username"));
                practitioner.setName(resultSet.getString("name"));
                practitioner.setSurnames(resultSet.getString("surname"));
                practitioners.add(practitioner);
            }
        } catch (SQLException sqlException) {
            Logger.getLogger(PractitionerDAO.class.getName())
                    .log(Level.SEVERE, sqlException.getMessage(), sqlException);
            return null;
        }
        return practitioners;
    }

    @Override
    public Practitioner getUserByUsername(String studentEnrollment) {
        Practitioner practitioner = null;
        String query = "SELECT * FROM Practitioner INNER JOIN User ON Practitioner.Username = User.Username AND Practitioner.Username = ?";
        try (Connection connection = mySQLConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, studentEnrollment);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while(resultSet.next()) {
                    practitioner = new Practitioner();
                    fillPractitioner(practitioner, resultSet);
                }
            }
        } catch (SQLException sqlException) {
            Logger.getLogger(PractitionerDAO.class.getName())
                    .log(Level.SEVERE, sqlException.getMessage(), sqlException);
        }
        return practitioner;
    }

    @Override
    public boolean addUser(Practitioner practitioner) {
        if (insertIntoUserTable(practitioner)) {
            return insertIntoPractitionerTable(practitioner);
        } else {
            return false;
        }
    }

    @Override
    public boolean deleteUser(Practitioner practitioner) {
        return (updateStatusUserTable(practitioner));
    }
        
    private boolean updateStatusUserTable(Practitioner practitioner) {
        boolean result = false;
        String query = "UPDATE User SET status = false WHERE username = ?";
        try (Connection connection = mySQLConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, practitioner.getUsername());
            int numberRowsAffected = preparedStatement.executeUpdate();
            result = (numberRowsAffected > 0);
        } catch (SQLException sqlException) {
            Logger.getLogger(PractitionerDAO.class.getName()).log(Level.SEVERE, 
                    sqlException.getMessage(), sqlException);
        }
        return result;
    }

    private boolean insertIntoUserTable(Practitioner practitioner) {
        boolean result = false;
        String query = "INSERT INTO User(Username,password,name,surname,userType,status) VALUES (?,?,?,?,?,?)";
        String passwordAux = BCrypt.hashpw(practitioner.getPassword(), BCrypt.gensalt(10));
        try (Connection connection = mySQLConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, practitioner.getUsername());
            preparedStatement.setString(2, passwordAux);
            preparedStatement.setString(3, practitioner.getName());
            preparedStatement.setString(4, practitioner.getSurnames());
            preparedStatement.setString(5, practitioner.getUserType());
            preparedStatement.setBoolean(6, practitioner.isActive());
            int numberRowsAffected = preparedStatement.executeUpdate();
            result = (numberRowsAffected > 0);
        } catch (SQLException sqlException) {
            Logger.getLogger(PractitionerDAO.class.getName())
                    .log(Level.SEVERE, sqlException.getMessage());
        }
        return result;
    }

    private boolean insertIntoPractitionerTable(Practitioner practitioner) {
        boolean result = false;
        String query = "INSERT INTO Practitioner(Username,shift,GroupID) VALUES(?,?,?)";
        try (Connection connection = mySQLConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, practitioner.getUsername());
            preparedStatement.setString(2, practitioner.getShift());
            preparedStatement.setInt(3, practitioner.getGroupID());
            int numberRowsAffected = preparedStatement.executeUpdate();
            result = (numberRowsAffected > 0);
        } catch (SQLException sqlException) {
            Logger.getLogger(PractitionerDAO.class.getName())
                    .log(Level.SEVERE, sqlException.getMessage());
        }
        return result;
    }
    
    public final void getPractitionerInformation(ObservableList<Practitioner> listPractitioner) {
        String query = "SELECT * FROM Practitioner INNER JOIN User ON Practitioner.Username = User.Username";
        try (Connection connection = mySQLConnection.getConnection();
             Statement instruction = connection.createStatement();
             ResultSet resultQuery = instruction.executeQuery(query)) {
            while(resultQuery.next()){
                Practitioner practitioner = new Practitioner();
                fillPractitioner(practitioner, resultQuery);
                listPractitioner.add(practitioner);
            }
        } catch (SQLException sqlException) {
            Logger.getLogger(PractitionerDAO.class.getName())
                    .log(Level.SEVERE, sqlException.getMessage(), sqlException);
        }
    }

    private void fillPractitioner(Practitioner practitioner, ResultSet resultSet) throws SQLException{
        practitioner.setUsername(resultSet.getString("Username"));
        practitioner.setPassword(resultSet.getString("password"));
        practitioner.setName(resultSet.getString("name"));
        practitioner.setSurnames(resultSet.getString("surname"));
        practitioner.setUserType(resultSet.getString("userType"));
        practitioner.setActive(resultSet.getBoolean("status"));
        practitioner.setShift(resultSet.getString("shift"));
        practitioner.setGroupID(resultSet.getInt("GroupID"));
    }
}
