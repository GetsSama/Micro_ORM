package edu.zhuravlev.sql.micro_orm.db_connection;

import edu.zhuravlev.sql.micro_orm.sql_tools.SQLUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class TmpConnectionRealization {
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
    private TmpConnectionRealization(){};

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
            System.out.println("Connection with DB successful!");
        } catch (SQLException e) {
            SQLUtils.printSQLException(e);
            throw new RuntimeException(e);
        }
    }
}
