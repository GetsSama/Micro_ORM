package edu.zhuravlev.sql.exampleTest;

import edu.zhuravlev.sql.example.ConnectionManager;
import edu.zhuravlev.sql.example.EntityKeeper;
import edu.zhuravlev.sql.example.KeeperFactory;

import java.sql.*;
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

        Person person = new Person(1, "Nikolay", "zurik.n", "RU", "secret");
        EntityKeeper userKeeper = KeeperFactory.createEntityKeeper(Person.class, connection);
        System.out.println(userKeeper);
        userKeeper.save(person);

        ConnectionManager.close();
    }
}
