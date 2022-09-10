package edu.zhuravlev.sql.example;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

class KeeperPool {
    private static Map<Class,EntityKeeper> entityMapping = new HashMap<>();

    private KeeperPool(){};

    public static boolean isContainsMapping(Class entityClass) {
        Objects.requireNonNull(entityClass);
        return entityMapping.containsKey(entityClass);
    }

    public static EntityKeeper getMapping(Class entityClass) {
        Objects.requireNonNull(entityClass);
        return entityMapping.get(entityClass);
    }

    public static void addMapping(Class entityClass, EntityKeeper keeper) {
        Objects.requireNonNull(entityClass);
        Objects.requireNonNull(keeper);
        entityMapping.put(entityClass,keeper);
    }

    public static void removeMapping(Class entityClass) {
        Objects.requireNonNull(entityClass);
        entityMapping.remove(entityClass);
    }
}
