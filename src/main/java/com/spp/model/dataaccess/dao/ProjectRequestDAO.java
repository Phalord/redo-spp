package com.spp.model.dataaccess.dao;

import com.spp.model.dataaccess.idao.IProjectRequestDAO;
import com.spp.model.domain.Project;
import com.spp.model.domain.ProjectRequest;
import com.spp.utils.MySQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProjectRequestDAO implements IProjectRequestDAO {
    private final MySQLConnection mySQLConnection = new MySQLConnection();

    @Override
    public final List<ProjectRequest> getListed() {
        return null;
    }

    @Override
    public final ProjectRequest getByID(int id) {
        return null;
    }

    @Override
    public final boolean addElement(ProjectRequest projectRequest) {
        boolean result = false;
        if (addProjectRequest(projectRequest)) {
            projectRequest.setProjectRequestID(getProjectRequestID(projectRequest));
            for (Project project: projectRequest.getProjectOptions()) {
                result = addRelationshipWithProjects(projectRequest, project);
            }
        }
        return result;
    }

    @Override
    public final boolean deleteElement(int id) {
        boolean result = false;
        String query = "UPDATE ProjectRequest SET status = false WHERE ProjectRequestID = ?";
        try (Connection connection = mySQLConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            int numberRowsAffected = preparedStatement.executeUpdate();
            result = (numberRowsAffected > 0);
        } catch (SQLException sqlException) {
            Logger.getLogger(ProjectRequestDAO.class.getName())
                    .log(Level.SEVERE, sqlException.getMessage(), sqlException);
        }
        return result;
    }

    @Override
    public final ProjectRequest getProjectRequestByStudentEnrollment(String studentEnrollment) {
        ProjectRequest projectRequest = null;
        String query = "SELECT ProjectRequestID, StudentEnrollment, status FROM ProjectRequest WHERE StudentEnrollment = ?";
        try (Connection connection = mySQLConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, studentEnrollment);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    projectRequest = new ProjectRequest();
                    projectRequest.setProjectRequestID(resultSet.getInt("ProjectRequestID"));
                    projectRequest.setRequestedBy(resultSet.getString("StudentEnrollment"));
                    projectRequest.setPending(resultSet.getBoolean("status"));
                }
            }
        } catch (SQLException sqlException) {
            Logger.getLogger(ProjectRequestDAO.class.getName())
                    .log(Level.SEVERE, sqlException.getMessage(), sqlException);
        }
        return projectRequest;
    }

    @Override
    public final List<ProjectRequest> getPendingRequests() {
        List<ProjectRequest> projectRequests = new ArrayList<>();
        String query = "SELECT ProjectRequestID,requestedAt,studentEnrollment FROM ProjectRequest WHERE status = true";
        try (Connection connection = mySQLConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                ProjectRequest projectRequest = new ProjectRequest();
                projectRequest.setProjectRequestID(resultSet.getInt("ProjectRequestID"));
                projectRequest.setRequestedAt(resultSet.getTimestamp("requestedAt"));
                projectRequest.setRequestedBy(resultSet.getString("studentEnrollment"));
                projectRequests.add(projectRequest);
            }
        } catch (SQLException sqlException) {
            Logger.getLogger(ProjectRequestDAO.class.getName())
                    .log(Level.SEVERE, sqlException.getMessage(), sqlException);
            projectRequests = null;
        }
        return projectRequests;
    }

    private int getProjectRequestID(ProjectRequest projectRequest) {
        int result = 0;
        String query = "SELECT ProjectRequestID FROM ProjectRequest WHERE StudentEnrollment = ?";
        try (Connection connection = mySQLConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, projectRequest.getRequestedBy());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    result = resultSet.getInt("ProjectRequestID");
                }
            }
        } catch (SQLException sqlException) {
            Logger.getLogger(ProjectRequestDAO.class.getName())
                    .log(Level.SEVERE, sqlException.getMessage(), sqlException);
        }
        return result;
    }

    private boolean addProjectRequest(ProjectRequest projectRequest) {
        boolean result = false;
        String query = "INSERT INTO ProjectRequest(requestedAt,status,StudentEnrollment) VALUES (?,?,?)";
        try (Connection connection = mySQLConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setTimestamp(1, projectRequest.getRequestedAt());
            preparedStatement.setBoolean(2, projectRequest.isPending());
            preparedStatement.setString(3, projectRequest.getRequestedBy());
            int numberRowsAffected = preparedStatement.executeUpdate();
            result = (numberRowsAffected > 0);
        } catch (SQLException sqlException) {
            Logger.getLogger(ProjectRequestDAO.class.getName())
                    .log(Level.SEVERE, sqlException.getMessage(), sqlException);
        }
        return result;
    }

    private boolean addRelationshipWithProjects(ProjectRequest projectRequest, Project project) {
        boolean result = false;
        String query = "INSERT INTO ProjectRequest_Project(ProjectRequestID,ProjectID) VALUES (?,?)";
        try (Connection connection = mySQLConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, projectRequest.getProjectRequestID());
            preparedStatement.setInt(2, project.getProjectID());
            int numberRowsAffected = preparedStatement.executeUpdate();
            result = (numberRowsAffected > 0);
        } catch (SQLException sqlException) {
            Logger.getLogger(ProjectRequestDAO.class.getName())
                    .log(Level.SEVERE, sqlException.getMessage(), sqlException);
        }
        return  result;
    }
}
