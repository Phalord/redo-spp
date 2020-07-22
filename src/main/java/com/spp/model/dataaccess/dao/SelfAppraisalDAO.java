package com.spp.model.dataaccess.dao;

import com.spp.model.dataaccess.idao.ISelfAppraisalDAO;
import com.spp.model.domain.SelfAppraisal;
import com.spp.utils.MySQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SelfAppraisalDAO implements ISelfAppraisalDAO {
    MySQLConnection mySQLConnection = new MySQLConnection();

    @Override
    public List<SelfAppraisal> getListed() {
        return null;
    }

    @Override
    public SelfAppraisal getByID(int id) {
        return null;
    }

    @Override
    public boolean addElement(SelfAppraisal selfAppraisal) {
        boolean result = false;
        String query = "INSERT INTO SelfAppraisal(Author,firstSentence,secondSentence,thirdSentence,fourthSentence,fifthSentence) VALUES (?,?,?,?,?,?)";
        try (Connection connection = mySQLConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, selfAppraisal.getAuthor().getUsername());
            preparedStatement.setByte(2, selfAppraisal.getFirstSentence());
            preparedStatement.setByte(3, selfAppraisal.getSecondSentence());
            preparedStatement.setByte(4, selfAppraisal.getThirdSentence());
            preparedStatement.setByte(5, selfAppraisal.getFourthSentence());
            preparedStatement.setByte(6, selfAppraisal.getFifthSentence());
            int numberRowsAffected = preparedStatement.executeUpdate();
            result = (numberRowsAffected > 0);
        } catch (SQLException sqlException) {
            Logger.getLogger(SelfAppraisalDAO.class.getName())
                    .log(Level.SEVERE, sqlException.getMessage(), sqlException);
        }
        return result;
    }

    @Override
    public boolean deleteElement(int id) {
        return false;
    }
}
