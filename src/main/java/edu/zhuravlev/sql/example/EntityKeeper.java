package edu.zhuravlev.sql.example;


import java.util.List;

public interface EntityKeeper {
    void save(Object entity);

    void save(List<Object> entityList);

    void update(Object entity);

    Object read(String id);

    List<Object> readAll();

    void delete(Object entity);

    void delete(List<String> deletedEntities);

    void dropTable();
}
