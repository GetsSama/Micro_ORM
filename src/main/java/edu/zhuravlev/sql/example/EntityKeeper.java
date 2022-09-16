package edu.zhuravlev.sql.example;


import java.sql.SQLException;
import java.util.List;

public interface EntityKeeper {
    void save(Object entity);

    void saveAll(List<Object> entityList);

    void update(Object entity);

    Object read(String id);

    List<Object> readAll();

    void delete(Object entity);

    void deleteAll(List<Object> deletedEntities);

    void dropTable();
}
