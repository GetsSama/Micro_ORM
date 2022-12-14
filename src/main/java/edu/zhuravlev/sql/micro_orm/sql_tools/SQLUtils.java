package edu.zhuravlev.sql.micro_orm.sql_tools;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import edu.zhuravlev.sql.micro_orm.entity_tools.EntityAnalyser;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.*;

public class SQLUtils {
    private SQLUtils(){};
    private static final Map<String, Method> dataTypesMapping;
    private static final Map<String, Method> dataReadTypesMapping;

    private static final Map<String, Class<?>> typeAndClass;

    static {
        try {
            dataTypesMapping = new HashMap<>();
            dataTypesMapping.put("int4", PreparedStatement.class.getMethod("setInt", int.class, int.class));
            dataTypesMapping.put("text", PreparedStatement.class.getMethod("setString", int.class, String.class));
            dataTypesMapping.put("varchar", PreparedStatement.class.getMethod("setString", int.class, String.class));
            dataReadTypesMapping = new HashMap<>();
            dataReadTypesMapping.put("int", ResultSet.class.getMethod("getInt", int.class));
            dataReadTypesMapping.put("String", ResultSet.class.getMethod("getString", int.class));
            typeAndClass = new HashMap<>();
            typeAndClass.put("int", int.class);
            typeAndClass.put("String", String.class);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public static Method getResultSetReadMethod(String expectedType) {
        return dataReadTypesMapping.get(expectedType);
    }

    public static Class<?> getClassByType(String type) {
        return typeAndClass.get(type);
    }

    public static final BiMap<String, String> typesSQLToJava = ImmutableBiMap.of(
            "int4", "int",
            "varchar", "String"
    );

    private static List<Object> getAttributesAsObjs (Map<String, String> nameAndType, String... attributesValues) {
        List<Object> atrObj = new ArrayList<>(attributesValues.length);
        int counter = 0;

        for (String key : nameAndType.keySet()) {
            //String javaType = sqlTypeAsJavaType.get(nameAndType.get(key));
            String javaType = typesSQLToJava.get(nameAndType.get(key));
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

    public static boolean isDBContainsMapping (Connection connection, String tableName, Class<?> entityClass) {
        Objects.requireNonNull(connection);
        Objects.requireNonNull(tableName);

        List<String> entityFields = Arrays.asList(EntityAnalyser.getFieldsName(entityClass));
        List<String> tableFieldsName = new ArrayList<>();
        String query = "SELECT * FROM " + tableName + " LIMIT 3";

        try(ResultSet rs = connection.getMetaData().getTables(connection.getCatalog(), null, tableName, null)) {
            if (!rs.next())
                return false;
        } catch (SQLException e) {
            printSQLException(e);
            throw new RuntimeException(e);
        }

        try(Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnsCount = metaData.getColumnCount();
            for (int i=1; i<=columnsCount; i++)
                tableFieldsName.add(metaData.getColumnLabel(i));
        } catch (SQLException e) {
            printSQLException(e);
            throw new RuntimeException(e);
        }

        if (entityFields.size() == tableFieldsName.size()) {
            for (String field : tableFieldsName) {
                boolean flag = false;
                for (String entityField : entityFields) {
                    if (field.equalsIgnoreCase(entityField)) {
                        flag = true;
                        break;
                    }
                }
                if (!flag)
                    return false;
            }
        } else
            return false;

        return true;
    }

    public static void setPrepareStatementParams (PreparedStatement prSt, Map<String, String> nameAndTypes, String... attrValues) {
        List<Object> attrValuesAsObj = getAttributesAsObjs(nameAndTypes, attrValues);
        int counter = 0;

        for (String key : nameAndTypes.keySet()) {
            Method setMethod = dataTypesMapping.get(nameAndTypes.get(key));

            try {
                setMethod.invoke(prSt, counter+1, attrValuesAsObj.get(counter));
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }

            counter++;
        }
    }

    public static void printGeneratedSQL(String sql) {
        System.out.println("Generated SQL -----> " + sql);
    }

    public static void printExecutedResult(int number, String operation) {
        System.out.println("Executed result: " + operation + " " + number + " rows");
    }

}
