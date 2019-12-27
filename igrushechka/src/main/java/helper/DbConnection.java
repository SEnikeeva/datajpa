package helper;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DbConnection {

    private static Connection connection = null;
    private String properties;

    public String getProperties() {
        return properties;
    }

    public DbConnection(String properties) {

        this.properties = properties;
    }

    public DbConnection() {
        this.properties = "db.properties";
    }

    public Connection getConnection()  {

        if (connection != null)
            return connection;
        else {

            Properties prop = new Properties();
            InputStream inputStream = DbConnection.class.getClassLoader().getResourceAsStream(properties);
            try {
                prop.load(inputStream);
            } catch (IOException e) {
                throw new IllegalArgumentException(e);
            }
            String driver = prop.getProperty("driver");
            String url = prop.getProperty("url");
            String user = prop.getProperty("user");
            String password = prop.getProperty("password");
            try {
                Class.forName(driver);
            } catch (ClassNotFoundException e) {
                throw new IllegalArgumentException(e);
            }
            try {
                connection = DriverManager.getConnection(url, user, password);
            } catch (SQLException e) {
                throw new IllegalStateException(e);
            }

            return connection;
        }

    }
}