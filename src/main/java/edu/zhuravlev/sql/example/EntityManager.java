package edu.zhuravlev.sql.example;

import java.sql.Connection;
import java.util.List;

public interface EntityManager {
    void save(Object entity);

    void saveAll(List<Object> entityList);

    void update(Object entity);

    Object read(String id, Class<?> entityClass);

    List<Object> readAll(Class<?> entityClass);

    void delete(Object entity);

    void deleteAll(List<Object> deletedEntities);

    void dropTable(Class<?> entityClass);
}
