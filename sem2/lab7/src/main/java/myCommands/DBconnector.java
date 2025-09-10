package myCommands;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class    DBconnector {
    private static final String URL = "jdbc:postgresql://pg:5432/studs";
    private static final String USER = "s466221";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
         return connection;
    };
}

