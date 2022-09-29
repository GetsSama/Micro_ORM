package edu.zhuravlev.sql.example;

import java.sql.Connection;
import java.util.Map;
import java.util.Objects;

public class KeeperFactory {
    private KeeperFactory(){};

    public static <T> EntityKeeper<T> createEntityKeeper(Class<T> entityClass, Connection connection) {
        Objects.requireNonNull(entityClass);
        Objects.requireNonNull(connection);


        if (KeeperPool.isContainsMapping(entityClass))
            return KeeperPool.getMapping(entityClass);
        else {
            Map<String, String> fieldsNameAndType = EntityAnalyser.getFieldsNameAndType(entityClass);
            String tableName = entityClass.getSimpleName().toLowerCase();

            EntityKeeper<T> entityKeeper = new EntityKeeperImpl<>(entityClass, fieldsNameAndType, connection, tableName);
            KeeperPool.addMapping(entityClass, entityKeeper);

            return entityKeeper;
        }
    }
}
