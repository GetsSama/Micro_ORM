package edu.zhuravlev.sql.example;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

class MetaDataPool {
    private static final Map<Class<?>, EntityMetaData> entityMapping = new HashMap<>();

    private MetaDataPool(){};

    public static boolean isContainsMapping(Class<?> entityClass) {
        Objects.requireNonNull(entityClass);
        return entityMapping.containsKey(entityClass);
    }

    public static EntityMetaData getMetaData(Object entity) {
        Objects.requireNonNull(entity);
        return entityMapping.get(entity.getClass());
    }

    public static void addMetaData(Class<?> entityClass, EntityMetaData entityMetaData) {
        Objects.requireNonNull(entityClass);
        Objects.requireNonNull(entityMetaData);
        entityMapping.put(entityClass, entityMetaData);
    }

    public static void removeMapping(Class<?> entityClass) {
        Objects.requireNonNull(entityClass);
        entityMapping.remove(entityClass);
    }
}
