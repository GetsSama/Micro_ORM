package edu.zhuravlev.sql.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionManager {
    private static final String url = "jdbc:postgresql://localhost/postgres";
    private static final String user = "postgres";
    private static final String pass = "520621df";

    private static Connection connectionInst;
    private static Statement st;

    private ConnectionManager(){};

    public static Connection getConnection() {
        if (connectionInst == null)
            createConnect();
        return connectionInst;
    }
    private static void createConnect() {
        try (Connection connection = DriverManager.getConnection(url, user, pass)) {
            connectionInst = connection;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
