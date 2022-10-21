package edu.zhuravlev.sql.example;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class KeeperPool {
    private static final Map<Class<?>,EntityKeeper<?>> entityMapping = new HashMap<>();

    private KeeperPool(){};

    public static boolean isContainsMapping(Class<?> entityClass) {
        Objects.requireNonNull(entityClass);
        return entityMapping.containsKey(entityClass);
    }

    @SuppressWarnings("unchecked")
    public static <T> EntityKeeper<T> getMapping(Class<T> entityClass) {
        Objects.requireNonNull(entityClass);
        return (EntityKeeper<T>) entityMapping.get(entityClass);
    }

    public static void addMapping(Class<?> entityClass, EntityKeeper<?> keeper) {
        Objects.requireNonNull(entityClass);
        Objects.requireNonNull(keeper);
        entityMapping.put(entityClass,keeper);
    }

    public static void removeMapping(Class<?> entityClass) {
        Objects.requireNonNull(entityClass);
        entityMapping.remove(entityClass);
    }
}
