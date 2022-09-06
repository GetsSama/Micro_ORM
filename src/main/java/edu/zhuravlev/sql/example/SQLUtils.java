package edu.zhuravlev.sql.example;

import java.lang.reflect.Method;
import java.sql.*;
import java.util.*;
import java.util.function.Function;

public class SQLUtils {
    private SQLUtils(){};
    private static Map<String, Method> dataTypesMapping = new HashMap<>();
    public static void printSQLException(SQLException ex){
        for (Throwable e: ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }

    public static Map<String, String> getTableSchema(Connection connection, String tableName) {
        Objects.requireNonNull(connection);
        Map<String, String> nameAndType = new LinkedHashMap<>();

        try (ResultSet rs = connection.getMetaData().getTables(connection.getCatalog(), null, tableName, null)) {
            if (rs.next()) {
                try (Statement statement = connection.createStatement()) {
                    ResultSet selectedTable = statement.executeQuery("select * from " + tableName + ";");
                    ResultSetMetaData rsmd = selectedTable.getMetaData();

                    for (int i=1; i<= rsmd.getColumnCount(); i++)
                        nameAndType.put(rsmd.getColumnLabel(i), rsmd.getColumnTypeName(i));
                }
            } else {
                throw new RuntimeException("In this DB no such tables with this table name!");
            }
        } catch (SQLException e) {
            printSQLException(e);
            throw new RuntimeException(e);
        }

        return nameAndType;
    }
}
