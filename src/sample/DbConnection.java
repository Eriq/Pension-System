package sample;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbConnection {

    public Connection getConnection() {

        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost/pension", "postgres", "123");
            return connection;
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
