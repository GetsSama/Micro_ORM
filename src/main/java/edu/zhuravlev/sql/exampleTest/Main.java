package edu.zhuravlev.sql.exampleTest;

import edu.zhuravlev.sql.example.ConnectionManager;

import java.sql.*;
import java.util.List;
import java.util.Scanner;

import static edu.zhuravlev.sql.example.TableCreator.*;

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



        ConnectionManager.close();
    }
}
