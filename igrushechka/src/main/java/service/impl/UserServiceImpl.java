package service.impl;

import helper.DbConnection;
import model.User;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserServiceImpl implements service.UserService {
    private Connection connection;


    public UserServiceImpl() {
        this.connection = new DbConnection().getConnection();
    }


    public User getUser(String login) {
        try {
            PreparedStatement ps = connection.prepareStatement("select * from users where login = (?)");
            ps.setString(1, login);
            ResultSet resultSet = ps.executeQuery();
            resultSet.next();
            return new User(resultSet.getInt("id"), resultSet.getString("login")
                    , resultSet.getString("password"), resultSet.getInt("score"));
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }

    }

    @Override
    public boolean checkUser(String login) {
        try {
            PreparedStatement ps = connection.prepareStatement("select * from users where login = (?)");
            ps.setString(1, login);
            return ps.executeQuery().next();
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public boolean checkPassword(String login, String password) {
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement("select * from users where login = (?)");
            int i = 1;
            ps.setString(i, login);
            ResultSet resultSet = ps.executeQuery();
            return resultSet.next() && password.equals(resultSet.getString("password"));
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
