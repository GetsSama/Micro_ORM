package edu.zhuravlev.sql.micro_orm.sql_tools;

import java.util.Map;

public class SQLCreator {
    private SQLCreator() {};

    private static final String CREATE_TEMPLATE = "CREATE TABLE %s ";

    public static String getCreateStatement(String tableName, String idFieldName, Map<String, String> fieldsNameAndType) {
        String firstPart = String.format(CREATE_TEMPLATE, tableName);
        int size = fieldsNameAndType.size();
        int counter = 1;

        StringBuilder builder = new StringBuilder(firstPart);
        builder.append("(");

        for(Map.Entry<String, String> pair : fieldsNameAndType.entrySet()) {
            String name = pair.getKey();
            String typeJava = pair.getValue();
            String typeSQL = SQLUtils.typesSQLToJava.inverse().get(typeJava);

            if(name.equalsIgnoreCase(idFieldName))
                builder.append(name.toUpperCase() + " " + typeSQL.toUpperCase() + " " + "PRIMARY KEY");
            else
                builder.append(name.toUpperCase() + " " + typeSQL.toUpperCase());

            if(counter != size)
                builder.append(", ");

            counter++;
        }

        builder.append(");");

        return builder.toString();
    }


    public static String getSelectStatement(String tableName, String idFieldName,  String id) {
        String firstPart = "SELECT * FROM " + tableName;
        String secondPart = " WHERE " + idFieldName + "='" + id + "'";
        return firstPart + secondPart;
    }

    public static String getInsertStatement(String tableName, Map<String, String> fieldsNameAndType, String[] values) {
        String firstPart = "INSERT INTO " + tableName + " (";
        StringBuilder builder = new StringBuilder(firstPart);
        StringBuilder builderValues = new StringBuilder("(");
        int size = fieldsNameAndType.size();
        int counter = 1;

        for (Map.Entry<String, String> pair : fieldsNameAndType.entrySet()) {
            if (counter != size) {
                builder.append(pair.getKey() + ", ");
                builderValues.append("'" + values[counter-1] + "'" + ", ");
            } else {
                builder.append(pair.getKey() + ") VALUES ");
                builderValues.append("'" + values[counter-1]+ "'" + ")");
            }
            counter++;
        }

        return builder.toString() + builderValues.toString();
    }

    public static String getUpdateStatement(String tableName, Map<String, String> updatableFieldAndValue, String id) {
        String firstPart = "UPDATE " + tableName + " SET ";
        StringBuilder builder = new StringBuilder(firstPart);
        int size = updatableFieldAndValue.size();
        int counter = 1;

        for (Map.Entry<String, String> pair : updatableFieldAndValue.entrySet()) {
            if (counter != size)
                builder.append(pair.getKey() + "='" + pair.getValue() + "', ");
            else
                builder.append(pair.getKey() + "='" + pair.getValue() + "' ");

            counter++;
        }

        builder.append("WHERE id='" + id + "'");

        return builder.toString();
    }

    public static String getDeleteStatement(String tableName, String id) {
        String firstPart = "DELETE FROM %s WHERE id='%s'";
        return String.format(firstPart, tableName, id);
    }

    public static String getDropStatement(String tableName) {
        return "DROP TABLE " + tableName;
    }
}
