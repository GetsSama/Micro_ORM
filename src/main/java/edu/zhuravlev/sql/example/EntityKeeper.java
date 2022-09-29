package edu.zhuravlev.sql.example;


import java.util.List;

public interface EntityKeeper<T> {
    void save(T entity);

    void saveAll(List<T> entityList);

    void update(T entity);

    T read(String id);

    List<T> readAll();

    void delete(T entity);

    void deleteAll(List<T> deletedEntities);

    void dropTable();
}
