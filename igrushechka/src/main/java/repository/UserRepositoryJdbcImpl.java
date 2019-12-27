package repository;


import helper.DbConnection;
import model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryJdbcImpl implements UserRepository {

    private Connection connection;

    public UserRepositoryJdbcImpl() {
        this.connection = new DbConnection().getConnection();
    }

    @Override
    public void save(User model)  {
        PreparedStatement st = null;
        try {
            st = connection.prepareStatement("INSERT INTO users(login, password, score) VALUES (?, ?, ?)");
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
        int i = 1;
        try {
            st.setString(i, model.getLogin());
            st.setString(++i, model.getPassword());
            st.setInt(++i, model.getScore());
            st.executeUpdate();
            st.close();
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }


    }

    @Override
    public User findByID(int id) {

        for (User user : findAll()) {
            if (user.getId() == id) {
                return user;
            }
        }

        return null;
    }

    public User findByLogin(String login) {
        for (User user : findAll()) {
            if (user.getLogin().equals(login)) {
                return user;
            }
        }

        return null;
    }

    @Override
    public void delete(User model) {

    }

    @Override
    public List<User> findAll() {

        List<User> users = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users ");
            while (resultSet.next()) {
                users.add(new User(resultSet.getInt("id"), resultSet.getString("login"),
                        resultSet.getString("password"), resultSet.getInt("score")));
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }


        return users;
    }

    @Override
    public void update() {

    }
}
