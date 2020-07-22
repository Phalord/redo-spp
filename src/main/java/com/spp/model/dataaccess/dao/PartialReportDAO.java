package com.spp.model.dataaccess.dao;

import com.spp.model.dataaccess.idao.IPartialReportDAO;
import com.spp.model.domain.PartialReport;
import com.spp.utils.MySQLConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PartialReportDAO implements IPartialReportDAO {
    MySQLConnection mySQLConnection = new MySQLConnection();

    @Override
    public List<PartialReport> getListed() {
        return null;
    }

    @Override
    public PartialReport getByID(int id) {
        return null;
    }

    @Override
    public boolean addElement(PartialReport partialReport) {
        if (insertIntoReportTable(partialReport)) {
            return insertIntoPartialReportTable(partialReport);
        } else {
            return false;
        }
    }

    @Override
    public boolean deleteElement(int id) {
        return false;
    }

    private boolean insertIntoReportTable(PartialReport partialReport) {
        boolean result = false;
        String query = "INSERT INTO Report(ReportID, reportType) VALUES (?,?)";
        try (Connection connection = mySQLConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, partialReport.getReportID());
            preparedStatement.setString(2, partialReport.getReportType());
            int numberRowsAffected = preparedStatement.executeUpdate();
            result = (numberRowsAffected > 0);
        } catch (SQLException sqlException) {
            Logger.getLogger(PartialReportDAO.class.getName())
                    .log(Level.SEVERE, sqlException.getMessage(), sqlException);
        }
        return result;
    }

    private boolean insertIntoPartialReportTable(PartialReport partialReport) {
        boolean result = false;
        String query = "INSERT INTO PartialReport(ReportID, projectHoursCovered, reportNumber, partialPeriod) VALUES (?,?,?,?)";
        try (Connection connection = mySQLConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, partialReport.getReportID());
            preparedStatement.setShort(2, partialReport.getProjectHoursCovered());
            preparedStatement.setByte(3, partialReport.getReportNumber());
            preparedStatement.setString(4, partialReport.getPartialPeriod());
            int numberRowsAffected = preparedStatement.executeUpdate();
            result = (numberRowsAffected > 0);
        } catch (SQLException sqlException) {
            Logger.getLogger(PartialReportDAO.class.getName())
                    .log(Level.SEVERE, sqlException.getMessage(), sqlException);
        }
        return result;
    }
}
