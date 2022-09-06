package edu.zhuravlev.sql.example;

import java.sql.*;
import java.util.Map;
import java.util.Objects;

public class TableInsert {
    private static final String defaultInsertSQL = "INSERT INTO users" +
            "  (id, name, email, country, password) VALUES " +
            " (?, ?, ?, ?, ?);";

    private final String tableName;
    private Map<String, String> schema;
    private static Connection _connection;

    public TableInsert(Connection connection, String tableName) {
        this.tableName = tableName;
        _connection = connection;
    }

    public static void defaultInsert() {
        Objects.requireNonNull(_connection);
        try (PreparedStatement preparedStatement = _connection.prepareStatement(defaultInsertSQL)) {
            preparedStatement.setInt(1,1);
            preparedStatement.setString(2, "Nikolay");
            preparedStatement.setString(3, "zurik.n@gmail.com");
            preparedStatement.setString(4, "RU");
            preparedStatement.setString(5, "secret");

            preparedStatement.executeUpdate();
            System.out.println("Executed ---> " + preparedStatement);
        } catch (SQLException e) {
            SQLUtils.printSQLException(e);
            throw new RuntimeException(e);
        }
    }

    private String[] getTableSchema() {
        Objects.requireNonNull(_connection);
        try {
            ResultSet table = _connection.getMetaData().getTables(_connection.getCatalog(), null, tableName, null);
            ResultSetMetaData tableInfo = table.getMetaData();

        } catch (SQLException e) {
            SQLUtils.printSQLException(e);
            throw new RuntimeException(e);
        }
        return null;
    }

    private String generateQueryByTableName(){
        return null;
    }
}
