package edu.zhuravlev.sql.example;

import java.sql.Connection;
import java.util.Objects;

public class KeeperFactory {
    private KeeperFactory(){};

    public static EntityKeeper createEntityKeeper(Class entityClass, Connection connection) {
        Objects.requireNonNull(entityClass);
        Objects.requireNonNull(connection);

        if (KeeperPool.isContainsMapping(entityClass))
            return KeeperPool.getMapping(entityClass);
        else {
            String[] fieldsName = EntityAnalyser.getFieldsName(entityClass);
            String[] fieldsType = EntityAnalyser.getFieldsType(entityClass);
            String tableName = entityClass.getSimpleName();

            EntityKeeper entityKeeper = new EntityKeeperImpl(fieldsName, fieldsType, connection, tableName);
            KeeperPool.addMapping(entityClass, entityKeeper);

            return entityKeeper;
        }
    }
}
