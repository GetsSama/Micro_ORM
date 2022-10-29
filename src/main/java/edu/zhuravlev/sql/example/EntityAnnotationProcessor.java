package edu.zhuravlev.sql.example;

import java.util.List;

public interface EntityAnnotationProcessor {
    List<Class<?>> getEntityClasses ();
    String getTableName(Class<?> entityClass);
    String getIdName(Class<?> entityClass);
}
