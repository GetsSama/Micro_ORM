package edu.zhuravlev.sql.example;

import java.sql.*;
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
        String[] values = {};


        ConnectionManager.close();
    }
}
