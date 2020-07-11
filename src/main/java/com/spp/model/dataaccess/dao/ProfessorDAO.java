package com.spp.model.dataaccess.dao;

import com.spp.model.dataaccess.idao.IProfessorDAO;
import com.spp.model.domain.Professor;
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

public class ProfessorDAO implements IProfessorDAO {
    private final MySQLConnection mySQLConnection;

    public ProfessorDAO() {
        mySQLConnection = new MySQLConnection();
    }

    @Override
    public List<Professor> getAllUsers() {
        List<Professor> professors = new ArrayList<>();
        String query = "SELECT Username, name, surname FROM User WHERE userType = 'Professor'";
        try (Connection connection = mySQLConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Professor professor = new Professor();
                professor.setUsername(resultSet.getString("Username"));
                professor.setName(resultSet.getString("name"));
                professor.setSurnames(resultSet.getString("surname"));
                professors.add(professor);
            }
        } catch (SQLException sqlException) {
            Logger.getLogger(ProfessorDAO.class.getName())
                    .log(Level.SEVERE, sqlException.getMessage(), sqlException);
            return null;
        }
        return professors;
    }

    @Override
    public Professor getUserByUsername(String username) {
        Professor professor = null;
        String query = "SELECT * FROM Professor INNER JOIN User ON Professor.Username = User.Username AND Professor.Username = ?";
        try (Connection connection = mySQLConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    professor = new Professor();
                    professor.setUsername(resultSet.getString("Username"));
                    professor.setName(resultSet.getString("name"));
                    professor.setSurnames(resultSet.getString("surname"));
                    professor.setActive(resultSet.getBoolean("status"));
                    professor.setUserType(resultSet.getString("userType"));
                }
            }
        } catch (SQLException sqlException) {
            Logger.getLogger(ProfessorDAO.class.getName())
                    .log(Level.SEVERE, sqlException.getMessage(), sqlException);
        }
        return professor;
    }

    @Override
    public boolean addUser(Professor professor) {
        if (insertIntoUserTable(professor)) {
            return insertIntoProfessorTable(professor);
        } else {
            return false;
        }
    }

    @Override
    public boolean deleteUser(Professor professor) {
        return false;
    }

    private boolean insertIntoUserTable(Professor professor) {
        boolean result = false;
        String query = "INSERT INTO User(Username,password,name,surname,userType,status) VALUES (?,?,?,?,?,?)";
        String passwordAux = BCrypt.hashpw(professor.getPassword(), BCrypt.gensalt(10));
        try (Connection connection = mySQLConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, professor.getUsername());
            preparedStatement.setString(2, passwordAux);
            preparedStatement.setString(3, professor.getName());
            preparedStatement.setString(4, professor.getSurnames());
            preparedStatement.setString(5, professor.getUserType());
            preparedStatement.setBoolean(6, professor.isActive());
            int numberRowsAffected = preparedStatement.executeUpdate();
            result = (numberRowsAffected > 0);
        } catch (SQLException sqlException) {
            Logger.getLogger(ProfessorDAO.class.getName())
                    .log(Level.SEVERE, sqlException.getMessage(), sqlException);
            return false;
        }
        return result;
    }

    private boolean insertIntoProfessorTable(Professor professor) {
        boolean result = false;
        String query = "INSERT INTO Professor(Username) VALUES(?)";
        try (Connection connection = mySQLConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, professor.getUsername());
            int numberRowsAffected = preparedStatement.executeUpdate();
            result = (numberRowsAffected > 0);
        } catch (SQLException sqlException) {
            Logger.getLogger(ProfessorDAO.class.getName())
                    .log(Level.SEVERE, sqlException.getMessage(), sqlException);
            return false;
        }
        return result;
    }
}