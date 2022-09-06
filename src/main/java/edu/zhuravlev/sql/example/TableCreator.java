package edu.zhuravlev.sql.example;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class TableCreator {
    private static final String createTableSQL = "CREATE TABLE users " +
            "(ID INT PRIMARY KEY ," +
            " NAME TEXT, " +
            " EMAIL VARCHAR(50), " +
            " COUNTRY VARCHAR(50), " +
            " PASSWORD VARCHAR(50))";
    private static Connection connection;
    private TableCreator(){};
    public static void createDefaultTable() {
        if (connection != null) {
            try (Statement statement = connection.createStatement()) {
                statement.execute(createTableSQL);
                System.out.println("Executed ---> " + createTableSQL);
            } catch (SQLException e) {
                SQLUtils.printSQLException(e);
            }
        } else {
            throw new RuntimeException("No such connection to DB!");
        }
    }

    public static void setConnection(Connection _connection){
        connection = _connection;
    }

    public static void createTable(String sql){
        if (connection != null & sql != null){
            String[] words = sql.split(" ");

            if (words[0].equalsIgnoreCase("create") & words[1].equalsIgnoreCase("table")) {
                try (Statement statement = connection.createStatement()) {
                    statement.execute(sql);
                    System.out.println("Executed ---> " + sql);
                } catch (SQLException e) {
                    SQLUtils.printSQLException(e);
                }
            }

        } else {
            throw new RuntimeException("No such connection to DB!");
        }
    }
}
