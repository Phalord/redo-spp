package com.spp.model.dataaccess.dao;

import com.spp.model.dataaccess.idao.IActivityDAO;
import com.spp.model.domain.Activity;
import com.spp.model.domain.Practitioner;
import com.spp.model.domain.Professor;
import com.spp.utils.MySQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ActivityDAO implements IActivityDAO {
    private final MySQLConnection mySQLConnection = new MySQLConnection();

    @Override
    public List<Activity> getListed() {
        List<Activity> activities = new ArrayList<>();
        String query = "SELECT ActivityID, title, DeliveredBy, dueDate, CreatedBy FROM Activity";
        try (Connection connection = mySQLConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Activity activity = new Activity();
                activity.setActivityID(resultSet.getInt("ActivityID"));
                activity.setTitle("title");
                activity.setDueDate(resultSet.getTimestamp("dueDate"));
                Practitioner deliveredBy = new Practitioner();
                deliveredBy.setUsername(resultSet.getString("DeliveredBy"));
                activity.setDeliveredBy(deliveredBy);
                Professor createdBy = new Professor();
                createdBy.setUsername(resultSet.getString("CreatedBy"));
                activity.setCreatedBy(createdBy);
                activities.add(activity);
            }
        } catch (SQLException sqlException) {
            Logger.getLogger(ActivityDAO.class.getName())
                    .log(Level.SEVERE, sqlException.getMessage(), sqlException);
            activities = null;
        }
        return activities;
    }

    @Override
    public Activity getByID(int id) {
        Activity activity = null;
        String query = "SELECT * FROM Activity";
        try (Connection connection = mySQLConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                activity = new Activity();
                activity.setActivityID(resultSet.getInt("ActivityID"));
                activity.setTitle(resultSet.getString("title"));
                activity.setDescription(resultSet.getString("description"));
                activity.setEstimatedCompletionHours(resultSet.getShort("estimatedCompletionHours"));
                activity.setActualCompletionHours(resultSet.getShort("actualCompletionHours"));
                activity.setDeliveredAt(resultSet.getTimestamp("deliveredAt"));
                activity.setDueDate(resultSet.getTimestamp("dueDate"));
                Professor professor = new Professor();
                professor.setUsername(resultSet.getString("CreatedBy"));
                activity.setCreatedBy(professor);
                Practitioner practitioner = new Practitioner();
                practitioner.setUsername(resultSet.getString("DeliveredBy"));
                activity.setDeliveredBy(practitioner);
            }
        } catch (SQLException sqlException) {
            Logger.getLogger(ActivityDAO.class.getName())
                    .log(Level.SEVERE, sqlException.getMessage(), sqlException);
        }
        return activity;
    }

    @Override
    public boolean addElement(Activity activity) {
        boolean result = false;
        String query = "INSERT INTO Activity(title, description, estimatedCompletionHours, DeliveredBy, dueDate, CreatedBy) VALUES (?,?,?,?,?,?)";
        try (Connection connection = mySQLConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, activity.getTitle());
            preparedStatement.setString(2, activity.getDescription());
            preparedStatement.setShort(3, activity.getEstimatedCompletionHours());
            preparedStatement.setString(4, activity.getDeliveredBy().getUsername());
            preparedStatement.setTimestamp(5, activity.getDueDate());
            preparedStatement.setString(6, activity.getCreatedBy().getUsername());
            int numberRowsAffected = preparedStatement.executeUpdate();
            result = (numberRowsAffected > 0);
        } catch (SQLException sqlException){
            Logger.getLogger(ActivityDAO.class.getName())
                    .log(Level.SEVERE, sqlException.getMessage(), sqlException);
        }
        return result;
    }

    @Override
    public boolean deleteElement(int id) {
        return false;
    }

    public List<Activity> getPractitionerActivities(String studentEnrollment) {
        List<Activity> activities = new ArrayList<>();
        String query = "SELECT ActivityID, title, DeliveredBy, dueDate, CreatedBy FROM Activity WHERE DeliveredBy = ?";
        try (Connection connection = mySQLConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, studentEnrollment);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Activity activity = new Activity();
                    activity.setActivityID(resultSet.getInt("ActivityID"));
                    activity.setTitle("title");
                    activity.setDueDate(resultSet.getTimestamp("dueDate"));
                    Practitioner deliveredBy = new Practitioner();
                    deliveredBy.setUsername(resultSet.getString("DeliveredBy"));
                    activity.setDeliveredBy(deliveredBy);
                    Professor createdBy = new Professor();
                    createdBy.setUsername(resultSet.getString("CreatedBy"));
                    activity.setCreatedBy(createdBy);
                    activities.add(activity);
                }
            }
        } catch (SQLException sqlException) {
            Logger.getLogger(ActivityDAO.class.getName())
                    .log(Level.SEVERE, sqlException.getMessage(), sqlException);
            activities = null;
        }
        return activities;
    }

    public List<Activity> getOpenPractitionerActivities(String studentEnrollment,
                                                        Timestamp actualTime) {
        List<Activity> activities = new ArrayList<>();
        String query = "SELECT ActivityID, title, description, DeliveredBy, dueDate, CreatedBy FROM Activity WHERE DeliveredBy = ? AND dueDate > ? AND deliveredAt = '0000-00-00'";
        try (Connection connection = mySQLConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, studentEnrollment);
            preparedStatement.setTimestamp(2, actualTime);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Activity activity = new Activity();
                    activity.setActivityID(resultSet.getInt("ActivityID"));
                    activity.setTitle(resultSet.getString("title"));
                    activity.setDescription(resultSet.getString("description"));
                    activity.setDueDate(resultSet.getTimestamp("dueDate"));
                    Practitioner deliveredBy = new Practitioner();
                    deliveredBy.setUsername(resultSet.getString("DeliveredBy"));
                    activity.setDeliveredBy(deliveredBy);
                    Professor createdBy = new Professor();
                    createdBy.setUsername(resultSet.getString("CreatedBy"));
                    activity.setCreatedBy(createdBy);
                    activities.add(activity);
                }
            }
        } catch (SQLException sqlException) {
            Logger.getLogger(ActivityDAO.class.getName())
                    .log(Level.SEVERE, sqlException.getMessage(), sqlException);
            activities = null;
        }
        return activities;
    }
}
