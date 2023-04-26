package com.george.todoapp.dao;

import com.george.todoapp.model.User;
import com.george.todoapp.utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDao {
    public User validate(String username, String password) throws SQLException, ClassNotFoundException{
        User user = null;

        Class.forName("org.postgresql.ds.PGConnectionPoolDataSource");

        try (Connection connection = JDBCUtils.getConnection();
             PreparedStatement preparedStatement = connection
                     .prepareStatement("select * from users where username = ? and password = ?")) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setUsername(rs.getString("username"));
                user.setFirstName(rs.getString("first_name"));
            }
        } catch (SQLException e) {
            JDBCUtils.printSQLException(e);
        }
        return user;
    }
}
