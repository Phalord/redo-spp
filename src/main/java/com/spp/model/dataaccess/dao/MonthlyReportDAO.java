package com.spp.model.dataaccess.dao;

import com.spp.model.dataaccess.idao.IMonthlyReportDAO;
import com.spp.model.domain.Activity;
import com.spp.model.domain.MonthlyReport;
import com.spp.utils.MySQLConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MonthlyReportDAO implements IMonthlyReportDAO {
    private final MySQLConnection mySQLConnection = new MySQLConnection();

    @Override
    public List<MonthlyReport> getListed() {
        return null;
    }

    @Override
    public MonthlyReport getByID(int id) {
        return null;
    }

    @Override
    public boolean addElement(MonthlyReport monthlyReport) {
        if (insertIntoReportTable(monthlyReport)) {
            return insertActivitiesDescriptions(monthlyReport);
        } else {
            return false;
        }
    }

    @Override
    public boolean deleteElement(int id) {
        return false;
    }

    private boolean insertIntoReportTable(MonthlyReport monthlyReport) {
        boolean result = false;
        String query = "INSERT INTO Report(ReportID, reportType) VALUES (?,?)";
        try (Connection connection = mySQLConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, monthlyReport.getReportID());
            preparedStatement.setString(2, monthlyReport.getReportType());
            int numberRowsAffected = preparedStatement.executeUpdate();
            result = (numberRowsAffected > 0);
        } catch (SQLException sqlException) {
            Logger.getLogger(MonthlyReportDAO.class.getName())
                    .log(Level.SEVERE, sqlException.getMessage(), sqlException);
        }
        return result;
    }

    private boolean insertActivitiesDescriptions(MonthlyReport monthlyReport) {
        boolean result = false;
        String query = "INSERT INTO PractitionerDescriptions(ReportID, description, ActivityID) VALUES (?,?,?)";
        for (Activity activity: monthlyReport.getActivities()) {
            try (Connection connection = mySQLConnection.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, monthlyReport.getReportID());
                preparedStatement.setString(2, monthlyReport.getActivitiesDescription().get(0));
                monthlyReport.getActivitiesDescription().remove(0);
                preparedStatement.setInt(3, activity.getActivityID());
                int numberRowsAffected = preparedStatement.executeUpdate();
                result = (numberRowsAffected > 0);
            } catch (SQLException sqlException) {
                Logger.getLogger(MonthlyReportDAO.class.getName())
                        .log(Level.SEVERE, sqlException.getMessage(), sqlException);
            }
        }
        return result;
    }
}
