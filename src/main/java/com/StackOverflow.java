package com;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

public class StackOverflow
{
    private static Logger LOGGER = Logger.getLogger(StackOverflow.class.getName());

    public User getUserById(String userId){
        PreparedStatement preparedStatement;
        ResultSet resultSet = null;
        Connection connection = null;
        DBConnection dbConnection = null;
        if (userId == null) {
            throw new IllegalArgumentException("AssociateId cannot be null");
        }
        User user = new User();
        preparedStatement = null;
        try {
            connection = dbConnection.openConnection(properties, inputStream);
            String query = "Select * from SYSTEM_USER";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(I_User.associateId, userId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user.setAssociateId(resultSet.getString(I_User.associateId));
                user.setAssociatePassword(resultSet
                                                  .getString(I_User.associatePassword));
                user.setAssociateRole(resultSet.getInt(I_User.associateRole));
                user.setAssociateIsActive(resultSet
                                                  .getBoolean(I_User.associateIsActive));
                user.setAssociateEmail(resultSet
                                               .getString(I_User.associateEmail));
            }
        } catch (ClassNotFoundException e) {
            LOGGER.warning("Cannot return User Details. ClassNotFoundException occured.");
        } catch (SQLException e) {
            LOGGER.warning("Cannot return User Details. SQLException occured.");
        } catch (IOException e) {
            LOGGER.warning("Cannot return User Details. IOException occured.");
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    LOGGER.warning("Failed to close resultSet.");
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    LOGGER.warning("Failed to close statement.");
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    LOGGER.warning("Failed to close connection.");
                }
            }
        }
        return user;
    }
}
