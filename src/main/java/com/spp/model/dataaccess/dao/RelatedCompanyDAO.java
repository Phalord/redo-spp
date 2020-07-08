package com.spp.model.dataaccess.dao;

import com.spp.model.dataaccess.idao.IRelatedCompanyDAO;
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

public class RelatedCompanyDAO implements IRelatedCompanyDAO {
    private final MySQLConnection mySQLConnection;

    public RelatedCompanyDAO() {
        mySQLConnection = new MySQLConnection();
    }

    @Override
    public List<RelatedCompany> getListed() {
        List<RelatedCompany> relatedCompanies = new ArrayList<>();
        String query = "SELECT RelatedCompanyID, name, email, phone FROM RelatedCompany";
        try (Connection connection = mySQLConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                RelatedCompany relatedCompany = new RelatedCompany();
                relatedCompany.setRelatedCompanyID(resultSet.getInt("RelatedCompanyID"));
                relatedCompany.setName(resultSet.getString("name"));
                relatedCompany.setEmail(resultSet.getString("email"));
                relatedCompany.setPhone(resultSet.getString("phone"));
                relatedCompanies.add(relatedCompany);
            }
        } catch (SQLException sqlException) {
            Logger.getLogger(RelatedCompanyDAO.class.getName())
                    .log(Level.SEVERE, sqlException.getMessage(), sqlException);
            return null;
        }
        return relatedCompanies;
    }

    @Override
    public RelatedCompany getByID(int id) {
        RelatedCompany relatedCompany = null;
        String query = "SELECT * FROM RelatedCompany WHERE RelatedCompanyID = ?";
        try (Connection connection = mySQLConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    relatedCompany = new RelatedCompany();
                    relatedCompany.setRelatedCompanyID(resultSet.getInt("RelatedCompanyID"));
                    relatedCompany.setName(resultSet.getString("name"));
                    relatedCompany.setAddress(resultSet.getString("address"));
                    relatedCompany.setCity(resultSet.getString("city"));
                    relatedCompany.setEmail(resultSet.getString("email"));
                    relatedCompany.setPhone(resultSet.getString("phone"));
                    relatedCompany.setSector(resultSet.getString("sector"));
                    relatedCompany.setState(resultSet.getString("state"));
                }
            }
        } catch (SQLException sqlException) {
            Logger.getLogger(RelatedCompanyDAO.class.getName())
                    .log(Level.SEVERE, sqlException.getMessage(), sqlException);
        }
        return relatedCompany;
    }

    @Override
    public boolean addElement(RelatedCompany relatedCompany) {
        boolean result = false;
        String query = "INSERT INTO RelatedCompany(name, address, state, sector, phone, email, city) VALUES (?,?,?,?,?,?,?)";
        try (Connection connection = mySQLConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1, relatedCompany.getName());
            preparedStatement.setString(2, relatedCompany.getAddress());
            preparedStatement.setString(3, relatedCompany.getState());
            preparedStatement.setString(4, relatedCompany.getSector());
            preparedStatement.setString(5, relatedCompany.getPhone());
            preparedStatement.setString(6, relatedCompany.getEmail());
            preparedStatement.setString(7, relatedCompany.getCity());
            int numberArrowsAffected = preparedStatement.executeUpdate();
            result = (numberArrowsAffected > 0);
        } catch (SQLException sqlException) {
            Logger.getLogger(RelatedCompanyDAO.class.getName())
                    .log(Level.SEVERE, sqlException.getMessage(), sqlException);
        }

        return result;
    }

    @Override
    public boolean deleteElement(int id) {
        return false;
    }
}
