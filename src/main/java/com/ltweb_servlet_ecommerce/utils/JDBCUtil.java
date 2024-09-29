package com.ltweb_servlet_ecommerce.utils;

import com.ltweb_servlet_ecommerce.log.LoggerHelper;

import java.sql.*;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class JDBCUtil {
    public static Connection getConnection() {
        Connection connection = null;
        String message = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
//            Lưu trong /bin/setenv.bat của tomcat
            ResourceBundle resourceBundle = ResourceBundle.getBundle("env");
            connection = DriverManager.getConnection(resourceBundle.getString("DB_URL"), resourceBundle.getString("DB_USERNAME"), resourceBundle.getString("DB_PASSWORD"));
            if (connection == null) {
                message = "Connection is null;";
                LoggerHelper.logDetailedDangerMessage(new Exception(message), "SELECT");
            }
        } catch (ClassNotFoundException | MissingResourceException | SQLException e) {
            LoggerHelper.logDetailedDangerMessage(e, "SELECT");
            e.printStackTrace();
        }
        return connection;
    }


    public static void closeConnection(Connection connection, PreparedStatement statement, ResultSet resultSet) {
        try {
            if (connection != null) {
                connection.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
