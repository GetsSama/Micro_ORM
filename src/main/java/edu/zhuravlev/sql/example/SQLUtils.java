package edu.zhuravlev.sql.example;

import java.lang.reflect.Method;
import java.sql.*;
import java.util.*;
import java.util.function.Function;

public class SQLUtils {
    private SQLUtils(){};
    private static final Map<String, Method> dataTypesMapping;

    static {
        try {
            dataTypesMapping = new HashMap<>(Map.of(
                    "int4", PreparedStatement.class.getMethod("setInt", int.class, int.class),
                    "text", PreparedStatement.class.getMethod("setString", int.class, String.class),
                    "varchar", PreparedStatement.class.getMethod("setString", int.class, String.class)
            ));
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    private static final Map<String, String> sqlTypeAsJavaType = new HashMap<>(Map.of(
            "int4" ,"int",
            "text" , "String",
            "varchar" , "String"
    ));

    public static List<Object> getAttributesAsObjs (Map<String, String> nameAndType, String... attributesValues) {
        List<Object> atrObj = new ArrayList<>(attributesValues.length);
        int counter = 0;

        for (Map.Entry pair : nameAndType.entrySet()) {
            String javaType = sqlTypeAsJavaType.get(pair.getValue());
            if (javaType.equals("int"))
                atrObj.add(Integer.valueOf(attributesValues[counter]));
            else if (javaType.equals("String"))
                atrObj.add(attributesValues[counter]);
            counter++;
        }

        return atrObj;
    }

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

        if (isDBContainsTable(connection, tableName)) {
                try (Statement statement = connection.createStatement()) {
                    ResultSet selectedTable = statement.executeQuery("select * from " + tableName + ";");
                    ResultSetMetaData rsmd = selectedTable.getMetaData();

                    for (int i=1; i<= rsmd.getColumnCount(); i++)
                        nameAndType.put(rsmd.getColumnLabel(i), rsmd.getColumnTypeName(i));
                } catch (SQLException e) {
                    printSQLException(e);
                    throw new RuntimeException(e);
                }
        } else {
                throw new RuntimeException("In this DB no such tables with this table name!");
        }

        return nameAndType;
    }

    public static boolean isDBContainsTable (Connection connection, String tableName) {
        try (ResultSet rs = connection.getMetaData().getTables(connection.getCatalog(), null, tableName, null)) {
            return rs.next();
        } catch (SQLException e) {
            printSQLException(e);
            throw  new RuntimeException(e);
        }
    }

    //public static void setPrepareStatementParams (PreparedStatement prSt, List<>)
}
