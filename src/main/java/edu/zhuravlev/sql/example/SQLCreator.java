package edu.zhuravlev.sql.example;

import java.util.Map;

class SQLCreator {
    private static final String createTableSQL = "CREATE TABLE users " +
            "(ID INT PRIMARY KEY ," +
            " NAME TEXT, " +
            " EMAIL VARCHAR(50), " +
            " COUNTRY VARCHAR(50), " +
            " PASSWORD VARCHAR(50))";

    private SQLCreator() {};

    private static final String CREATE_TEMPLATE = "CREATE TABLE %s ";

    public static String CREATE_STATEMENT(String tableName, Map<String, String> fieldsNameAndType) {
        String firstPart = String.format(CREATE_TEMPLATE, tableName);
        int size = fieldsNameAndType.size();
        int counter = 1;

        StringBuilder builder = new StringBuilder(firstPart);
        builder.append("(");

        for(var pair : fieldsNameAndType.entrySet()) {
            String name = pair.getKey();
            String typeJava = pair.getValue();
            String typeSQL = SQLUtils.typesSQLToJava.inverse().get(typeJava);

            if(name.equalsIgnoreCase("id"))
                builder.append(name.toUpperCase() + " " + typeSQL.toUpperCase() + " " + "PRIMARY KEY");
            else
                builder.append(name.toUpperCase() + " " + typeSQL.toUpperCase());

            if(counter != size)
                builder.append(", ");

            counter++;
        }

        builder.append(")");

        return builder.toString();
    }
}
