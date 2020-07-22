package com.spp.model.dataaccess.dao;

import com.spp.model.dataaccess.idao.IProjectResponsibleDAO;
import com.spp.model.domain.ProjectResponsible;
import com.spp.utils.MySQLConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProjectResponsibleDAO implements IProjectResponsibleDAO {
    private final MySQLConnection mySQLConnection;

    public ProjectResponsibleDAO() {
        mySQLConnection = new MySQLConnection();
    }

    @Override
    public ProjectResponsible getResponsibleByCompanyID(int id) {
        ProjectResponsible projectResponsible = null;
        String query = "SELECT * FROM ProjectResponsible WHERE RelatedCompanyID = ?";
        try (Connection connection = mySQLConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    projectResponsible = new ProjectResponsible();
                    projectResponsible
                            .setProjectResponsibleID(resultSet.getInt("ProjectResponsibleID"));
                    projectResponsible.setName(resultSet.getString("name"));
                    projectResponsible.setPhone(resultSet.getString("phone"));
                    projectResponsible.setSurname(resultSet.getString("surname"));
                    projectResponsible.setEmail(resultSet.getString("email"));
                    projectResponsible.setRelatedCompanyID(resultSet.getInt("RelatedCompanyID"));
                }
            }
        } catch (SQLException sqlException) {
            Logger.getLogger(ProjectResponsibleDAO.class.getName())
                    .log(Level.SEVERE, sqlException.getMessage(), sqlException);
        }
        return projectResponsible;
    }

    @Override
    public List<ProjectResponsible> getListed() {
        List<ProjectResponsible> projectManagers = new ArrayList<>();
        String query = "SELECT * FROM ProjectResponsible";
        try (Connection connection = mySQLConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                ProjectResponsible projectResponsible = new ProjectResponsible();
                projectResponsible.setProjectResponsibleID(resultSet
                        .getInt("ProjectResponsibleID"));
                projectResponsible.setName("name");
                projectResponsible.setPhone("phone");
                projectManagers.add(projectResponsible);
            }
        } catch (SQLException sqlException) {
            Logger.getLogger(ProjectResponsibleDAO.class.getName())
                    .log(Level.SEVERE, sqlException.getMessage(), sqlException);
            return null;
        }
        return projectManagers;
    }

    @Override
    public ProjectResponsible getByID(int id) {
        ProjectResponsible projectResponsible = null;
        String query = "SELECT * FROM ProjectResponsible WHERE ProjectResponsibleID = ?";
        try (Connection connection = mySQLConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    projectResponsible = new ProjectResponsible();
                    projectResponsible
                            .setProjectResponsibleID(resultSet.getInt("ProjectResponsibleID"));
                    projectResponsible.setName(resultSet.getString("name"));
                    projectResponsible.setPhone(resultSet.getString("phone"));
                    projectResponsible.setEmail(resultSet.getString("email"));
                    projectResponsible.setSurname(resultSet.getString("surname"));
                    projectResponsible.setRelatedCompanyID(resultSet.getInt("RelatedCompanyID"));
                }
            }
        } catch (SQLException sqlException) {
            Logger.getLogger(ProjectResponsibleDAO.class.getName())
                    .log(Level.SEVERE, sqlException.getMessage(), sqlException);
        }
        return projectResponsible;
    }

    @Override
    public boolean addElement(ProjectResponsible projectResponsible) {
        boolean result = false;
        String query = "INSERT INTO ProjectResponsible(name,phone,surname,RelatedCompanyID,email) VALUES (?,?,?,?,?)";
        try (Connection connection = mySQLConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, projectResponsible.getName());
            preparedStatement.setString(2, projectResponsible.getPhone());
            preparedStatement.setString(3, projectResponsible.getSurname());
            preparedStatement.setInt(4, projectResponsible.getRelatedCompanyID());
            preparedStatement.setString(5, projectResponsible.getEmail());
            int numberRowsAffected = preparedStatement.executeUpdate();
            result = (numberRowsAffected > 0);
        } catch (SQLException sqlException) {
            Logger.getLogger(ProjectResponsibleDAO.class.getName())
                    .log(Level.SEVERE, sqlException.getMessage(), sqlException);
        }
        return result;
    }

    @Override
    public boolean deleteElement(int id) {
        return false;
    }
}
