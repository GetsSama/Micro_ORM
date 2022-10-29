package edu.zhuravlev.sql.exampleTest;

import edu.zhuravlev.sql.example.TmpConnectionRealization;
import edu.zhuravlev.sql.example.EntityKeeper;
import edu.zhuravlev.sql.example.EntityKeeperWrapper;

import java.sql.*;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Connection connection = TmpConnectionRealization.getConnection();


        Person person = new Person(1, "Nikolay", "zurik.n", "RU", "secret");
        Person person2 = new Person(2, "Sveta", "mathandmath", "RU", "secret");
        Person person3 = new Person(3, "John", "john.mail.eu", "EU", "secret");
        Person person1_new = new Person(1, "Nikolay", "mail", "EU", "secret");
        Person readPerson;
        EntityKeeper<Person> userKeeper = EntityKeeperWrapper.createEntityKeeper(Person.class, connection);
        System.out.println(userKeeper);

        try (Scanner scn = new Scanner(System.in)){
            while (scn.hasNext()) {
                String input = scn.next();
                if (input.equals("DeleteAll"))
                    userKeeper.deleteAll(Arrays.asList(person, person2, person3));
                else if (input.equals("Drop"))
                    userKeeper.dropTable();
                else if (input.equals("SaveAll"))
                    userKeeper.saveAll(Arrays.asList(person, person2, person3));
                else if (input.equals("Read")) {
                    readPerson = userKeeper.read("1");
                    System.out.println(person.equals(readPerson));
                }
                else if (input.equals("Update"))
                    userKeeper.update(person1_new);
                else if (input.equals("ReadAll")) {
                    List<Person> persons = userKeeper.readAll();
                    persons.forEach(System.out::println);
                    System.out.println(persons.equals(Arrays.asList(person, person2, person3)));
                }
                else if (input.equals("1"))
                    break;

            }
        }

        EntityKeeper<Car> carsKeeper = EntityKeeperWrapper.createEntityKeeper(Car.class, connection);
        System.out.println(carsKeeper);
        Car car1 = new Car("Nikolay", "Nissan GTR", 330, 550);
        Car car2 = new Car("Sveta", "RangeRover", 250, 400);
        Car car3 = new Car("John", "Opel", 250, 150);

        carsKeeper.saveAll(Arrays.asList(car1, car2));
        carsKeeper.save(car3);
        System.out.println(carsKeeper.readAll());
        car1.setModel("Nissan GTR R35");
        carsKeeper.update(car1);
        System.out.println(carsKeeper.readAll());
        carsKeeper.delete(car3);
        System.out.println(carsKeeper.readAll());
        //carsKeeper.dropTable();

        TmpConnectionRealization.close();
    }
}
