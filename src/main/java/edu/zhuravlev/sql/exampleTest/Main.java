package edu.zhuravlev.sql.exampleTest;

import edu.zhuravlev.sql.example.ConnectionManager;
import edu.zhuravlev.sql.example.EntityKeeper;
import edu.zhuravlev.sql.example.KeeperFactory;

import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Connection connection = ConnectionManager.getConnection();


        Person person = new Person(1, "Nikolay", "zurik.n", "RU", "secret");
        Person person2 = new Person(2, "Sveta", "mathandmath", "RU", "secret");
        EntityKeeper userKeeper = KeeperFactory.createEntityKeeper(Person.class, connection);
        System.out.println(userKeeper);

        try (Scanner scn = new Scanner(System.in)){
            while (scn.hasNext()) {
                String input = scn.next();
                if (input.equals("Nikolay"))
                    userKeeper.save(person);
                else if (input.equals("Sveta"))
                    userKeeper.save(person2);
                else if (input.equals("1"))
                    break;
            }
        }

        ConnectionManager.close();
    }
}
