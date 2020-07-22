package com.spp.model.dataaccess.dao;

import com.spp.model.dataaccess.idao.IProjectDAO;
import com.spp.model.domain.Project;
import com.spp.model.domain.ProjectAssignment;
import com.spp.model.domain.RelatedCompany;
import com.spp.utils.MySQLConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProjectDAO implements IProjectDAO {
    private final MySQLConnection mySQLConnection= new MySQLConnection();

    @Override
    public final Project getProjectByStudentEnrollment(String studentEnrollment) {
        Project project = null;
        String query = "SELECT Project.ProjectID, title, description, resources FROM ProjectAssignment INNER JOIN Project ON ProjectAssignment.ProjectID = Project.ProjectID AND ProjectAssignment.PractitionerAssigned = ?";
        try (Connection connection = mySQLConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, studentEnrollment);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    project = new Project();
                    project.setProjectID(resultSet.getInt("ProjectID"));
                    project.setTitle(resultSet.getString("title"));
                    project.setDescription(resultSet.getString("description"));
                    project.setResources(resultSet.getString("resources"));
                }
            }
        } catch (SQLException sqlException) {
            Logger.getLogger(ProjectDAO.class.getName())
                    .log(Level.SEVERE, sqlException.getMessage(), sqlException);
        }
        return project;
    }

    @Override
    public final List<Project> getProjectRequestedProjects(int projectRequestID) {
        List<Project> projects = new ArrayList<>();
        String query = "SELECT P.ProjectID, P.title, RC.name FROM ProjectRequest PR INNER JOIN ProjectRequest_Project PRP on PR.ProjectRequestID = PRP.ProjectRequestID INNER JOIN Project P on PRP.ProjectID = P.ProjectID INNER JOIN RelatedCompany RC on P.RelatedCompanyID = RC.RelatedCompanyID WHERE PR.ProjectRequestID = ? AND P.available = 'Available'";
        try (Connection connection = mySQLConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query))  {
            preparedStatement.setInt(1, projectRequestID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Project project = new Project();
                    project.setProjectID(resultSet.getInt("ProjectID"));
                    project.setTitle(resultSet.getString("title"));
                    RelatedCompany relatedCompany = new RelatedCompany();
                    relatedCompany.setName(resultSet.getString("name"));
                    project.setRequestedBy(relatedCompany);
                    projects.add(project);
                }
            }
        } catch (SQLException sqlException) {
            Logger.getLogger(ProjectDAO.class.getName())
                    .log(Level.SEVERE, sqlException.getMessage(), sqlException);
            projects = null;
        }
        return projects;
    }

    @Override
    public final List<Project> getListed() {
        List<Project> projects = new ArrayList<>();
        String query = "SELECT ProjectID, title, available FROM Project";
        try (Connection connection = mySQLConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Project project = new Project();
                project.setProjectID(resultSet.getInt("ProjectID"));
                project.setTitle(resultSet.getString("tile"));
                project.setStatus(resultSet.getString("available"));
                projects.add(project);
            }
        } catch (SQLException sqlException) {
            Logger.getLogger(ProjectDAO.class.getName())
                    .log(Level.SEVERE, sqlException.getMessage(), sqlException);
            return null;
        }
        return projects;
    }

    @Override
    public final Project getByID(int id) {
        Project project = null;
        String query = "SELECT * FROM Project WHERE ProjectID = ?";
        try (Connection connection = mySQLConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    project = new Project();
                    project.setProjectID(resultSet.getInt("ProjectID"));
                    project.setTitle(resultSet.getString("title"));
                    project.setDescription(resultSet.getString("description"));
                    project.setResources(resultSet.getString("resources"));
                    project.setStatus(resultSet.getString("available"));
                    RelatedCompany relatedCompany = new RelatedCompany();
                    relatedCompany.setRelatedCompanyID(resultSet.getInt("RelatedCompanyID"));
                    project.setRequestedBy(relatedCompany);
                }
            }
        } catch (SQLException sqlException) {
            Logger.getLogger(ProjectDAO.class.getName())
                    .log(Level.SEVERE, sqlException.getMessage(), sqlException);
        }
        return project;
    }

    @Override
    public final boolean addElement(Project project) {
        boolean result = false;
        String query = "INSERT INTO Project(title,description,resources,available,RelatedCompanyID,ProjectResponsibleID) VALUES (?,?,?,?,?,?)";
        try (Connection connection = mySQLConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, project.getTitle());
            preparedStatement.setString(2, project.getDescription());
            preparedStatement.setString(3, project.getResources());
            preparedStatement.setString(4, project.getStatus());
            preparedStatement.setInt(5, project.getRequestedBy().getRelatedCompanyID());
            preparedStatement.setInt(6,
                    project.getRequestedBy().getEmployee().getProjectResponsibleID());
            int numberRowsAffected = preparedStatement.executeUpdate();
            result = (numberRowsAffected > 0);
        } catch (SQLException sqlException) {
            Logger.getLogger(ProjectDAO.class.getName())
                    .log(Level.SEVERE, sqlException.getMessage(), sqlException);
        }
        return result;
    }

    @Override
    public boolean deleteElement(int id) {
        return false;
    }

    @Override
    public final List<Project> getAvailableProjects() {
        List<Project> projects = new ArrayList<>();
        String query = "SELECT p.ProjectID, p.title, p.Available, rc.name, rc.email FROM Project p INNER JOIN RelatedCompany rc on p.RelatedCompanyID = rc.RelatedCompanyID WHERE available = 'Available'";
        try (Connection connection = mySQLConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Project project = new Project();
                project.setProjectID(resultSet.getInt("ProjectID"));
                project.setTitle(resultSet.getString("title"));
                project.setStatus(resultSet.getString("Available"));
                RelatedCompany relatedCompany = new RelatedCompany();
                relatedCompany.setName(resultSet.getString("name"));
                relatedCompany.setEmail(resultSet.getString("email"));
                project.setRequestedBy(relatedCompany);
                projects.add(project);
            }
        } catch (SQLException sqlException) {
            Logger.getLogger(ProjectDAO.class.getName())
                    .log(Level.SEVERE, sqlException.getMessage(), sqlException);
            return null;
        }
        return projects;
    }

    @Override
    public boolean assignProject(ProjectAssignment projectAssignment) {
        if (makeAssignation(projectAssignment)) {
            return setProjectUnavailable(projectAssignment.getProject().getProjectID());
        } else {
            return false;
        }
    }

    private boolean makeAssignation(ProjectAssignment projectAssignment) {
        boolean result = false;
        String query = "INSERT INTO ProjectAssignment(status,AssignedBy,PractitionerAssigned, ProjectID) VALUES (?,?,?,?)";
        try (Connection connection = mySQLConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, projectAssignment.getStatus());
            preparedStatement.setString(2, projectAssignment.getAssignedBy().getUsername());
            preparedStatement.setString(3, projectAssignment.getPractitioner().getUsername());
            preparedStatement.setInt(4, projectAssignment.getProject().getProjectID());
            int numberRowsAffected = preparedStatement.executeUpdate();
            result = (numberRowsAffected > 0);
        } catch (SQLException sqlException) {
            Logger.getLogger(ProjectDAO.class.getName())
                    .log(Level.SEVERE, sqlException.getMessage(), sqlException);
        }
        return result;
    }

    private boolean setProjectUnavailable(int projectID) {
        boolean result = false;
        String query = "UPDATE Project SET available = 'Unavailable' WHERE ProjectID = ?";
        try (Connection connection = mySQLConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, projectID);
            int numberRowsAffected = preparedStatement.executeUpdate();
            result = (numberRowsAffected > 0);
        } catch (SQLException sqlException) {
            Logger.getLogger(ProjectDAO.class.getName())
                    .log(Level.SEVERE, sqlException.getMessage(), sqlException);
        }
        return result;
    }
}
