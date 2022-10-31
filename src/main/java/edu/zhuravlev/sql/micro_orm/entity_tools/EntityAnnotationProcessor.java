package edu.zhuravlev.sql.micro_orm.entity_tools;

import edu.zhuravlev.sql.micro_orm.annotations.Entity;

import java.util.List;

public interface EntityAnnotationProcessor {
    List<Class<?>> getEntityClasses ();
    String getTableName(Class<?> entityClass);
    String getIdName(Class<?> entityClass);
    static boolean isEntityClass(Class<?> clazz) {
        return clazz.isAnnotationPresent(Entity.class);
    }
}
