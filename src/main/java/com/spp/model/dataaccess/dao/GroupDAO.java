package com.spp.model.dataaccess.dao;

import com.spp.model.dataaccess.idao.IGroupDAO;
import com.spp.model.domain.Group;
import com.spp.model.domain.Professor;
import com.spp.utils.MySQLConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GroupDAO implements IGroupDAO {
    private final MySQLConnection mySQLConnection = new MySQLConnection();
    
    @Override
    public final List<Group> getListed() {
        List<Group> groups = new ArrayList<>();
        String query = "SELECT CP.GroupID, CP.educationalExperience, CP.nrc, CP.quota, P.Username, U.name FROM ClassGroup CP INNER JOIN Professor P on CP.Lecturer = P.Username INNER JOIN User U on P.Username = U.Username";
        try (Connection connection = mySQLConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Group group = new Group();
                group.setGroupID(resultSet.getInt("GroupID"));
                group.setEducationalExperience(resultSet.getString("educationalExperience"));
                group.setNrc(resultSet.getString("nrc"));
                group.setQuota(resultSet.getByte("quota"));
                Professor professor = new Professor();
                professor.setUsername(resultSet.getString("Username"));
                professor.setName(resultSet.getString("name"));
                group.setLecturer(professor);
                groups.add(group);
            }
        } catch (SQLException sqlException) {
            Logger.getLogger(GroupDAO.class.getName())
                    .log(Level.SEVERE, sqlException.getMessage(), sqlException);
            groups = null;
        }
        return groups;
    }

@Override
public final List<Group> getAvailableGroups() {
    List<Group> groups = new ArrayList<>();
    String query = "SELECT GroupID, nrc, shift FROM ClassGroup WHERE availableQuota > 0";
    try (Connection connection = mySQLConnection.getConnection();
         PreparedStatement statement = connection.prepareStatement(query);
         ResultSet resultSet = statement.executeQuery()) {
        while (resultSet.next()) {
            Group group = new Group();
            group.setGroupID(resultSet.getInt("GroupID"));
            group.setNrc(resultSet.getString("nrc"));
            group.setShift(resultSet.getString("shift"));
            groups.add(group);
        }
    } catch (SQLException sqlException) {
        Logger.getLogger(GroupDAO.class.getName())
                .log(Level.SEVERE, sqlException.getMessage(), sqlException);
        groups = null;
    }
    return groups;
}
    
    @Override
    public final List<Group> getProfessorAvailableGroups() {
        List<Group> groups = new ArrayList<>();
        String query = "SELECT GroupID, nrc, shift FROM ClassGroup WHERE Lecturer IS NULL";
        try (Connection connection = mySQLConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Group group = new Group();
                group.setGroupID(resultSet.getInt("GroupID"));
                group.setNrc(resultSet.getString("nrc"));
                group.setShift(resultSet.getString("shift"));
                groups.add(group);
            }
        } catch (SQLException sqlException) {
            Logger.getLogger(GroupDAO.class.getName())
                    .log(Level.SEVERE, sqlException.getMessage(), sqlException);
            groups = null;
        }
        return groups;
    }

    @Override
    public final Group getByID(int groupID) {
        Group group = null;
        String query = "SELECT CP.GroupID, CP.educationalExperience, CP.nrc, CP.quota, CP.shift, CP.weeklyHours, U.Username, U.name FROM ClassGroup CP INNER JOIN Professor P on CP.Lecturer = P.Username INNER JOIN User U on P.Username = U.Username WHERE CP.GroupID = ?";
        try (Connection connection = mySQLConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, groupID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    group = new Group();
                    group.setGroupID(resultSet.getInt("GroupID"));
                    group.setEducationalExperience(resultSet.getString("educationalExperience"));
                    group.setNrc(resultSet.getString("nrc"));
                    group.setQuota(resultSet.getByte("quota"));
                    group.setShift(resultSet.getString("shift"));
                    group.setWeeklyHours(resultSet.getByte("weeklyHours"));
                    Professor professor = new Professor();
                    professor.setUsername(resultSet.getString("Username"));
                    professor.setName(resultSet.getString("name"));
                    group.setLecturer(professor);
                }
            }
        } catch (SQLException sqlException) {
            Logger.getLogger(GroupDAO.class.getName())
                    .log(Level.SEVERE, sqlException.getMessage(), sqlException);
        }
        return group;
    }

    public final Group getProfessorGroup(String username) {
        Group group = null;
        String query = "SELECT CP.GroupID, CP.educationalExperience, CP.nrc, CP.quota, CP.shift, CP.weeklyHours, U.Username, U.name FROM ClassGroup CP INNER JOIN Professor P on CP.Lecturer = P.Username INNER JOIN User U on P.Username = U.Username WHERE CP.lecturer = ?";
        try (Connection connection = mySQLConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    group = new Group();
                    group.setGroupID(resultSet.getInt("GroupID"));
                    group.setEducationalExperience(resultSet.getString("educationalExperience"));
                    group.setNrc(resultSet.getString("nrc"));
                    group.setShift(resultSet.getString("shift"));
                    group.setQuota(resultSet.getByte("quota"));
                    group.setWeeklyHours(resultSet.getByte("weeklyHours"));
                    Professor professor = new Professor();
                    professor.setUsername(resultSet.getString("Username"));
                    professor.setName(resultSet.getString("name"));
                    group.setLecturer(professor);
                }
            }
        } catch (SQLException sqlException) {
            Logger.getLogger(GroupDAO.class.getName())
                    .log(Level.SEVERE, sqlException.getMessage(), sqlException);
        }
        return group;
    }

    @Override
    public final byte getAvailableQuota(int groupID) {
        int availableQuota = 0;
        String query = "SELECT CG.quota FROM ClassGroup CG INNER JOIN Practitioner P on CG.GroupID = P.GroupID INNER JOIN User U on P.Username = U.Username WHERE CG.GroupID = ? AND U.status = TRUE";
        try (Connection connection = mySQLConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, groupID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    availableQuota = resultSet.getInt("quota");
                    availableQuota--;
                }
                while (resultSet.next()) {
                    availableQuota--;
                }
            }
        } catch (SQLException sqlException) {
            Logger.getLogger(GroupDAO.class.getName())
                    .log(Level.SEVERE, sqlException.getMessage(), sqlException);
        }
        return (byte) availableQuota;
    }

    @Override
    public final boolean addElement(Group group) {
        boolean result = false;
        String query = "INSERT INTO ClassGroup(educationalExperience, nrc, quota, shift, weeklyHours, availableQuota) VALUES (?,?,?,?,?,?)";
        try (Connection connection = mySQLConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, group.getEducationalExperience());
            preparedStatement.setString(2, group.getNrc());
            preparedStatement.setByte(3, group.getQuota());
            preparedStatement.setString(4, group.getShift());
            preparedStatement.setByte(5, group.getWeeklyHours());
            preparedStatement.setByte(6, group.getQuota());
            int numberRowsAffected = preparedStatement.executeUpdate();
            result = (numberRowsAffected > 0);
        } catch (SQLException sqlException) {
            Logger.getLogger(GroupDAO.class.getName())
                    .log(Level.SEVERE, sqlException.getMessage(), sqlException);
        }
        return result;
    }
    
    @Override
    public final boolean assignLecturer(Group group) {
        boolean result = false;
        String query = "UPDATE ClassGroup SET lecturer = ? WHERE GroupID = ?";
        try (Connection connection = mySQLConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, group.getLecturer().getUsername());
            preparedStatement.setInt(2, group.getGroupID());
            int numberRowsAffected = preparedStatement.executeUpdate();
            result = (numberRowsAffected > 0);
        } catch (SQLException sqlException) {
            Logger.getLogger(GroupDAO.class.getName())
                    .log(Level.SEVERE, sqlException.getMessage(), sqlException);
        }
        return result;
    }

    public final boolean addPractitioner(Group group) {
        boolean result = false;
        String query = "UPDATE ClassGroup SET availableQuota = ? WHERE GroupID = ?";
        try (Connection connection = mySQLConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setByte(1, group.getQuota());
            preparedStatement.setInt(2, group.getGroupID());
            int numberRowsAffected = preparedStatement.executeUpdate();
            result = (numberRowsAffected > 0);
        } catch (SQLException sqlException) {
            Logger.getLogger(GroupDAO.class.getName())
                    .log(Level.SEVERE, sqlException.getMessage(), sqlException);
        }
        return result;
    }

    @Override
    public boolean deleteElement(int id) {
        return false;
    }
}
