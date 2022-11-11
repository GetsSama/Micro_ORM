package edu.zhuravlev.sql.exampleTest;


import edu.zhuravlev.sql.micro_orm.CommonProperties;
import edu.zhuravlev.sql.micro_orm.EntityManager;
import edu.zhuravlev.sql.micro_orm.db_connection.SimpleConnectionManagerImpl;
import edu.zhuravlev.sql.micro_orm.resources_manager.ResourcesAnalyzer;

import java.net.URL;

public class Main {
    public static void main(String[] args) {
        Person p1 = new Person(1, "Nikolay", "zurik.n", "RU", "secret");
        EntityManager em = EntityManager.createEntityManager();
        SimpleConnectionManagerImpl.close();
        CommonProperties properties = ResourcesAnalyzer.getDBProperties();
        System.out.println(properties);
    }
}
