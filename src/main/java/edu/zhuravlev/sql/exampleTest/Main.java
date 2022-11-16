package edu.zhuravlev.sql.exampleTest;


import edu.zhuravlev.sql.micro_orm.properties.CommonProperties;
import edu.zhuravlev.sql.micro_orm.EntityManager;
import edu.zhuravlev.sql.micro_orm.properties.ResourcesAnalyzer;

public class Main {
    public static void main(String[] args) {
        Person p1 = new Person(1, "Nikolay", "zurik.n", "RU", "secret");
        EntityManager em = EntityManager.createEntityManager();
        CommonProperties properties = ResourcesAnalyzer.getDBProperties();
        System.out.println(properties);
        Person p2 = em.read("1", Person.class);
        System.out.println(p2);
        System.out.println(p1.equals(p2));
        em.freeResources();
    }
}
