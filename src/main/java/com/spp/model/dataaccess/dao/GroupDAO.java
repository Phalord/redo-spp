package com.spp.model.dataaccess.dao;

import com.spp.model.dataaccess.idao.IGroupDAO;
import com.spp.model.domain.Group;
import com.spp.model.domain.Practitioner;
import com.spp.model.domain.Professor;
import com.spp.utils.MySQLConnection;
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

public class GroupDAO implements IGroupDAO {
    private final MySQLConnection mySQLConnection = new MySQLConnection();
    
    @Override
    public List<Group> getListed() {
        List<Group> groups = new ArrayList<>();
        String query = "SELECT GroupID, educationalExperience, nrc, quota, shift, weeklyHours, Lecturer FROM ClassGroup";
        try (Connection connection = mySQLConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Group group = new Group();
                group.setGroupID(resultSet.getInt("GroupID"));
                group.setEducationalExperience(resultSet.getString("educationalExperience"));
                group.setShift(resultSet.getString("shift"));
                group.setNrc(resultSet.getString("nrc"));
                group.setQuota(resultSet.getByte("quota"));
                group.setWeeklyHours(resultSet.getByte("weeklyHours"));
                Practitioner student = new Practitioner();
                student.setUsername(resultSet.getString("Lecturer"));
                group.setStudents((List<Practitioner>) student);
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
    public Group getByID(int id) {
        Group group = null;
        return group;
    }

    @Override
    public boolean addElement(Group t) {
        return false;
    }

    @Override
    public boolean deleteElement(int id) {
        return false;
    }
    
    public final void getGroupID(ObservableList<Group> listGroup) {
        String query = "SELECT * FROM ClassGroup";
        try (Connection connection = mySQLConnection.getConnection();
             Statement instruction = connection.createStatement();
             ResultSet resultSet = instruction.executeQuery(query)) {
            while(resultSet.next()){
                Group group = new Group();
                group.setGroupID(resultSet.getInt("GroupID"));
                group.setNrc(resultSet.getString("nrc"));
                listGroup.add(group);
            }
        } catch (SQLException sqlException) {
            Logger.getLogger(PractitionerDAO.class.getName())
                    .log(Level.SEVERE, sqlException.getMessage(), sqlException);
        }
    }
}
