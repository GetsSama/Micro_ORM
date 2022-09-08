package edu.zhuravlev.sql.example;

import java.sql.*;
import java.util.List;
import java.util.Scanner;

import static edu.zhuravlev.sql.example.TableCreator.*;
import static edu.zhuravlev.sql.example.SQLUtils.*;

public class Main {
    public static void main(String[] args) {
        Connection connection = ConnectionManager.getConnection();

        try (Scanner scn = new Scanner(System.in)){
            while (scn.hasNext()) {
                String input = scn.next();
                if (input.equals("create")) {
                    setConnection(connection);
                    createDefaultTable();
                } else if (input.equals("1"))
                    break;
            }
        }

        var nameAndAttrib = getTableSchema(connection, "users");
        String[] values = {"1", "Tony", "tony@gmail.com", "US", "secret"};
        //List<Object> atr = getAttributesAsObjs(nameAndAttrib, values);
        String sql = "INSERT INTO users" +
                "  (id, name, email, country, password) VALUES " +
                " (?, ?, ?, ?, ?);";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            System.out.println(preparedStatement);

            setPrepareStatementParams(preparedStatement, nameAndAttrib, values);

            System.out.println(preparedStatement);
        } catch (SQLException e) {
            printSQLException(e);
            throw new RuntimeException(e);
        }


        ConnectionManager.close();
    }
}
