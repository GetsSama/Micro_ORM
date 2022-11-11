package edu.zhuravlev.sql.micro_orm.db_connection;

import edu.zhuravlev.sql.micro_orm.properties.CommonProperties;
import edu.zhuravlev.sql.micro_orm.resources_manager.ResourcesAnalyzer;
import edu.zhuravlev.sql.micro_orm.sql_tools.SQLUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

class TmpConnectionRealization {
    private static final CommonProperties properties = ResourcesAnalyzer.getDBProperties();
    private static Connection connectionInst;

    static {
        try {
            Class.forName(properties.getProperty("dbDriver"));
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
            String url = properties.getProperty("url");
            String user = properties.getProperty("user");
            String pass = properties.getProperty("password");

            connectionInst = DriverManager.getConnection(url, user, pass);
            System.out.println("Connection with DB successful!");
        } catch (SQLException e) {
            SQLUtils.printSQLException(e);
            throw new RuntimeException(e);
        }
    }
}
