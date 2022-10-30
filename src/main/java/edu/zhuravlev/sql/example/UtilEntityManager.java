package edu.zhuravlev.sql.example;

import java.util.List;

public interface UtilEntityManager {
    void save(Object entity);

    void saveAll(List<Object> entityList);

    void update(Object entity);

    <T> T read(String id, Class<T> entityClass);

    <T> List<T> readAll(Class<T> entityClass);

    void delete(Object entity);

    void deleteAll(List<Object> deletedEntities);

    void dropTable(Class<?> entityClass);
}
