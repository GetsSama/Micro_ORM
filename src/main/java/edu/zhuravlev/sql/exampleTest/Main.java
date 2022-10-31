package edu.zhuravlev.sql.exampleTest;

import edu.zhuravlev.sql.micro_orm.EntityManager;
import edu.zhuravlev.sql.micro_orm.db_connection.SimpleConnectionManagerImpl;
import edu.zhuravlev.sql.micro_orm.entity_tools.EntityAnnotationProcessor;
import edu.zhuravlev.sql.micro_orm.entity_tools.SimpleEntityAnnotationProcessor;
import edu.zhuravlev.sql.micro_orm.resources_manager.ClassFinder;
import edu.zhuravlev.sql.micro_orm.resources_manager.ResourcesAnalyzer;

import java.sql.*;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        EntityManager em = EntityManager.createEntityManager();

        Car car = new Car("e123wr777", "Porche", 300, 450);
        em.save(car);

        SimpleConnectionManagerImpl.close();
    }
}
