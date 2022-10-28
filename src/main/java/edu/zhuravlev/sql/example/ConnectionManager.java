package edu.zhuravlev.sql.example;

import org.postgresql.ds.PGConnectionPoolDataSource;
import org.postgresql.ds.PGPooledConnection;
import org.postgresql.jdbc2.optional.ConnectionPool;

import javax.sql.DataSource;
import javax.sql.PooledConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    private static final String url = "jdbc:postgresql://localhost/crud_edu";
    private static final String user = "postgres";
    private static final String pass = "520621df";
    private static Connection connectionInst;

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    private ConnectionManager(){};

    public static Connection getConnection() {
        if (connectionInst == null)
            createConnect();
        return connectionInst;
    }

    public static void close() {
        if (connectionInst != null) {
            try {
                connectionInst.close();
            } catch (SQLException e) {
                SQLUtils.printSQLException(e);
                throw new RuntimeException(e);
            }
        }
        System.out.println("Connection closed!");
    }

    private static void createConnect() {
        try {
            connectionInst = DriverManager.getConnection(url, user, pass);
            System.out.println("Connection successful!");
        } catch (SQLException e) {
            SQLUtils.printSQLException(e);
            throw new RuntimeException(e);
        }
    }
}
