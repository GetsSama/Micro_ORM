package edu.zhuravlev.sql.exampleTest;

import edu.zhuravlev.sql.example.ConnectionManager;
import edu.zhuravlev.sql.example.EntityKeeper;
import edu.zhuravlev.sql.example.KeeperFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Connection connection = ConnectionManager.getConnection();

        try (Scanner scn = new Scanner(System.in)){
            while (scn.hasNext()) {
                String input = scn.next();
                if (input.equals("create")) {

                } else if (input.equals("1"))
                    break;
            }
        }

        EntityKeeper userKeeper = KeeperFactory.createEntityKeeper(User.class, connection);
        System.out.println(userKeeper);
        System.out.println("Check CREATE");
        userKeeper.save(new Object());
        System.out.println("Check INSERT");
        userKeeper.save(new ArrayList<>());
        System.out.println("Check SELECT");
        userKeeper.read("1");
        System.out.println("Check UPDATE");
        userKeeper.update(null);
        System.out.println("Check DELETE");
        userKeeper.delete("1");
        System.out.println("Check DROP");
        userKeeper.dropTable();

        ConnectionManager.close();
    }
}
