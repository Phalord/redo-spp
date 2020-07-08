package com.spp.model.dataaccess.dao;

import com.spp.model.dataaccess.idao.IProjectResponsibleDAO;
import com.spp.model.domain.ProjectResponsible;
import com.spp.utils.MySQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        return null;
    }

    @Override
    public ProjectResponsible getByID(int id) {
        return null;
    }

    @Override
    public boolean addElement(ProjectResponsible projectResponsible) {
        return false;
    }

    @Override
    public boolean deleteElement(int id) {
        return false;
    }
}
