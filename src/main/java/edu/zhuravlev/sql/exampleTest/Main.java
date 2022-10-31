package edu.zhuravlev.sql.exampleTest;


import edu.zhuravlev.sql.micro_orm.EntityManager;
import edu.zhuravlev.sql.micro_orm.db_connection.SimpleConnectionManagerImpl;

import java.net.URL;

public class Main {
    public static void main(String[] args) {
        Person p1 = new Person(1, "Nikolay", "zurik.n", "RU", "secret");
        EntityManager em = EntityManager.createEntityManager();
        Person p1_new = em.read("1", Person.class);
        System.out.println(p1_new);
        System.out.println(p1_new.equals(p1));
        SimpleConnectionManagerImpl.close();
    }
}
